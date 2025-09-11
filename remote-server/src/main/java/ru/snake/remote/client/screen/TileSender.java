package ru.snake.remote.client.screen;

import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;
import ru.snake.remote.eventloop.client.ClientSender;

public class TileSender {

	private final ClientSender sender;

	private int nCreated;

	private int nCached;

	public TileSender(final ClientSender sender) {
		this.sender = sender;
		this.nCreated = 0;
		this.nCached = 0;
	}

	public int getCreated() {
		return nCreated;
	}

	public int getCached() {
		return nCached;
	}

	public void send(final CreatedTile tile) {
		sender.sendCreatedTile(tile.getX(), tile.getY(), tile.getIndex(), tile.getData());
		nCreated += 1;
	}

	public void send(final CachedTile tile) {
		sender.sendCachedTile(tile.getX(), tile.getY(), tile.getIndex());
		nCached += 1;
	}

	@Override
	public String toString() {
		return "TileSender [sender=" + sender + "]";
	}

}
