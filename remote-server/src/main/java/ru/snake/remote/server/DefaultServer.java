package ru.snake.remote.server;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;
import ru.snake.remote.core.tile.Tile;
import ru.snake.remote.eventloop.server.ServerReceiver;
import ru.snake.remote.eventloop.server.ServerSender;
import ru.snake.remote.server.screen.ImageCanvas;

public class DefaultServer implements ServerReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultServer.class);

	private final ServerSender sender;

	private final ImageCanvas canvas;

	private final BlockingQueue<Tile> tileQueue;

	public DefaultServer(final ServerSender sender, final ImageCanvas canvas, final BlockingQueue<Tile> tileQueue) {
		this.sender = sender;
		this.canvas = canvas;
		this.tileQueue = tileQueue;
	}

	@Override
	public void onClearTiles() {
	}

	@Override
	public void onScreenSize(int width, int height) {
		try {
			SwingUtilities.invokeAndWait(() -> canvas.setImageSize(width, height));
		} catch (InvocationTargetException | InterruptedException e) {
			LOG.warn("Failed to change screen size", e);
		}
	}

	@Override
	public void onCreateTile(int x, int y, int index, byte[] data) {
		CreatedTile tile = new CreatedTile(x, y, index, data);

		try {
			tileQueue.put(tile);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void onCachedTile(int x, int y, int index) {
		CachedTile tile = new CachedTile(x, y, index);

		try {
			tileQueue.put(tile);
		} catch (InterruptedException e) {
		}
	}

}
