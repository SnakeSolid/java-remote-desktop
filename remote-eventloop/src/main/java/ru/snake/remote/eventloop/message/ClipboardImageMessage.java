package ru.snake.remote.eventloop.message;

import java.util.Arrays;

public class ClipboardImageMessage {

	private int width;

	private int height;

	private int[] data;

	public ClipboardImageMessage() {
		this.width = 0;
		this.height = 0;
		this.data = null;
	}

	public ClipboardImageMessage(final int width, final int height, final int[] data) {
		this.width = width;
		this.height = height;
		this.data = data;
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

	public int[] getData() {
		return data;
	}

	public void setData(int[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ClipboardImageMessage [width=" + width + ", height=" + height + ", data=" + Arrays.toString(data) + "]";
	}

}
