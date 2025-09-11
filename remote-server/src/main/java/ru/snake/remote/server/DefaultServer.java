package ru.snake.remote.server;

import java.util.concurrent.BlockingQueue;

import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;
import ru.snake.remote.core.tile.Tile;
import ru.snake.remote.eventloop.server.ServerReceiver;
import ru.snake.remote.eventloop.server.ServerSender;

public class DefaultServer implements ServerReceiver {

	private final ServerSender sender;

	private final BlockingQueue<Tile> tileQueue;

	public DefaultServer(final ServerSender sender, final BlockingQueue<Tile> tileQueue) {
		this.sender = sender;
		this.tileQueue = tileQueue;
	}

	@Override
	public void onClearTiles() {
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
