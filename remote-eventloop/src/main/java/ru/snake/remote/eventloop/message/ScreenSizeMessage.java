package ru.snake.remote.eventloop.message;

public class ScreenSizeMessage {

	private int width;

	private int height;

	public ScreenSizeMessage() {
		this.width = 0;
		this.height = 0;
	}

	public ScreenSizeMessage(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "ScreenSizeMessage [width=" + width + ", height=" + height + "]";
	}

}
