package ru.snake.remote.eventloop.message;

import java.util.Arrays;

public class CreatedTileMessage {

	private int x;

	private int y;

	private int index;

	private byte[] data;

	public CreatedTileMessage() {
		this.x = 0;
		this.y = 0;
		this.index = 0;
		this.data = null;
	}

	public CreatedTileMessage(final int x, final int y, final int index, final byte[] data) {
		this.x = x;
		this.y = y;
		this.index = index;
		this.data = data;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CreatedTileMessage [x=" + x + ", y=" + y + ", index=" + index + ", data=" + Arrays.toString(data) + "]";
	}

}
