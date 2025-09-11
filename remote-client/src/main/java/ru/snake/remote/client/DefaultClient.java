package ru.snake.remote.client;

import ru.snake.remote.client.screen.ScreenLoop;
import ru.snake.remote.eventloop.client.ClientReceiver;
import ru.snake.remote.eventloop.client.ClientSender;

public class DefaultClient implements ClientReceiver {

	private final ClientSender sender;

	private final ScreenLoop screenLoop;

	public DefaultClient(final ClientSender sender, final ScreenLoop screenLoop) {
		this.sender = sender;
		this.screenLoop = screenLoop;
	}

	@Override
	public void onClearTiles() {
		screenLoop.clearCache();
	}

}
