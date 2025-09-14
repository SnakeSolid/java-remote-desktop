package ru.snake.remote.server.screen;

import java.lang.reflect.InvocationTargetException;
import java.util.Queue;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.remote.core.TiledDecompressor;
import ru.snake.remote.core.block.BlockDecompressor;
import ru.snake.remote.core.block.BlockDecompressorFactory;
import ru.snake.remote.core.block.CompressionQuality;
import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;
import ru.snake.remote.core.tile.Tile;

public class ScreenLoop implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ScreenLoop.class);

	private static final long CAPTURE_INTERVAL_MS = 150;

	private final ImageCanvas canvas;

	private final Queue<Tile> tileQueue;

	private final TiledDecompressor decompressor;

	public ScreenLoop(final ImageCanvas canvas, final Queue<Tile> tileQueue) {
		this.canvas = canvas;
		this.tileQueue = tileQueue;
		this.decompressor = new TiledDecompressor();
	}

	public synchronized void setQuality(final CompressionQuality quality) {
		BlockDecompressor blockDecompressor = BlockDecompressorFactory.forQuality(quality);

		synchronized (decompressor) {
			decompressor.setDecompressor(blockDecompressor);
		}
	}

	@Override
	public void run() {
		LOG.info("Screen loop started.");

		Thread thread = Thread.currentThread();

		while (!thread.isInterrupted()) {
			long startTime = System.currentTimeMillis();

			synchronized (decompressor) {
				while (true) {
					Tile tile = tileQueue.poll();

					if (tile == null) {
						break;
					} else if (tile instanceof CreatedTile) {
						decompressor.decompress((CreatedTile) tile);
					} else if (tile instanceof CachedTile) {
						decompressor.decompress((CachedTile) tile);
					} else {
						throw new RuntimeException("Unknown tile type: " + tile);
					}
				}
			}

			try {
				SwingUtilities.invokeAndWait(() -> {
					canvas.setImageTile(0, 0, decompressor.getImage());
					canvas.repaint();
				});
			} catch (InvocationTargetException | InterruptedException e) {
				LOG.error("Failed to update canvas image.", e);

				break;
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

}
