package ru.snake.remote.client.screen;

import ru.snake.remote.eventloop.client.ClientSender;

public class ScreenSizeSender {

	private final ClientSender sender;

	private int width;

	private int height;

	public ScreenSizeSender(final ClientSender sender) {
		this.sender = sender;
		this.width = 0;
		this.height = 0;
	}

	public void send(int screenWidth, int screenHeight) {
		if (width != screenWidth || height != screenHeight) {
			width = screenWidth;
			height = screenHeight;

			sender.sendScreenSize(width, height);
		}
	}

	@Override
	public String toString() {
		return "ScreenSizeSender [sender=" + sender + ", width=" + width + ", height=" + height + "]";
	}

}
