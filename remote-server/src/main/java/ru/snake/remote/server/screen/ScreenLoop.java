package ru.snake.remote.server.screen;

import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.remote.core.TiledDecompressor;
import ru.snake.remote.core.block.BlockDecompressor;
import ru.snake.remote.core.block.BlockDecompressorFactory;

public class ScreenLoop implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ScreenLoop.class);

	private static final long CAPTURE_INTERVAL_MS = 150;

	private final ImageCanvas canvas;

	private final Queue<ScreenOperation> tileQueue;

	private final TiledDecompressor decompressor;

	public ScreenLoop(final ImageCanvas canvas, final BlockingQueue<ScreenOperation> screenQueue) {
		this.canvas = canvas;
		this.tileQueue = screenQueue;
		this.decompressor = new TiledDecompressor();
	}

	@Override
	public void run() {
		LOG.info("Screen loop started.");

		Thread thread = Thread.currentThread();

		while (!thread.isInterrupted()) {
			long startTime = System.currentTimeMillis();
			boolean screenUpdated = false;

			while (true) {
				ScreenOperation operation = tileQueue.poll();

				if (operation == null) {
					break;
				}

				if (operation.getQuality() != null) {
					BlockDecompressor blockDecompressor = BlockDecompressorFactory.forQuality(operation.getQuality());

					decompressor.setDecompressor(blockDecompressor);
				} else if (operation.getCachedTile() != null) {
					decompressor.decompress(operation.getCachedTile());
					screenUpdated = true;
				} else if (operation.getCreatedTile() != null) {
					decompressor.decompress(operation.getCreatedTile());
					screenUpdated = true;
				} else if (operation.isScreenSync()) {
					break;
				}
			}

			if (screenUpdated) {
				try {
					SwingUtilities.invokeAndWait(() -> {
						canvas.setImageTile(0, 0, decompressor.getImage());
						canvas.repaint();
					});
				} catch (InvocationTargetException | InterruptedException e) {
					LOG.error("Failed to update canvas image.", e);

					break;
				}
			}

			long endTime = System.currentTimeMillis();
			long delta = endTime - startTime;
			LOG.info("Screen update time = {}.", delta);

			try {
				Thread.sleep(Math.max(CAPTURE_INTERVAL_MS - delta, 0));
			} catch (InterruptedException e) {
				break;
			}
		}

		LOG.info("Screen loop stopped.");
	}

	public void start(final String remoteAddress) {
		Thread screenLoop = new Thread(this, String.format("Screen loop (%s)", remoteAddress));
		screenLoop.setDaemon(true);
		screenLoop.start();
	}

}
