package ru.snake.remote.client.screen;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.remote.core.TiledCompressor;
import ru.snake.remote.eventloop.client.ClientSender;

public class ScreenLoop implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ScreenLoop.class);

	private static final long CAPTURE_INTERVAL_MS = 150;

	private final TiledCompressor compressor;

	private final ClientSender sender;

	public ScreenLoop(final ClientSender sender) {
		this.compressor = new TiledCompressor();
		this.sender = sender;
	}

	public void clearCache() {
		synchronized (compressor) {
			compressor.clearCache();
		}
	}

	@Override
	public void run() {
		LOG.info("Screen loop started.");

		Thread thread = Thread.currentThread();
		RobotWrapper robot = RobotWrapper.create();
		long startTime = System.currentTimeMillis();

		while (!thread.isInterrupted()) {
			BufferedImage screen = robot.createScreenCapture();
			TileSender tileSender = new TileSender(sender);

			synchronized (compressor) {
				compressor.compress(screen, tileSender::send, tileSender::send);
			}

			long endTime = System.currentTimeMillis();
			long delta = endTime - startTime;
			LOG.info(
				"Screen update time = {}, cached tiles = {}, created tiles = {}.",
				delta,
				tileSender.getCached(),
				tileSender.getCreated()
			);
			startTime = endTime;

			try {
				Thread.sleep(Math.max(CAPTURE_INTERVAL_MS - delta, 0));
			} catch (InterruptedException e) {
				break;
			}
		}

		LOG.info("Screen loop stopped.");
	}

}
