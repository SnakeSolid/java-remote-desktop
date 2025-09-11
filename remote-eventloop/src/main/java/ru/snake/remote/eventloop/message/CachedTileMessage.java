package ru.snake.remote.eventloop.message;

public class CachedTileMessage {

	private int x;

	private int y;

	private int index;

	public CachedTileMessage() {
		this.x = 0;
		this.y = 0;
		this.index = 0;
	}

	public CachedTileMessage(final int x, final int y, final int index) {
		this.x = x;
		this.y = y;
		this.index = index;
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

	@Override
	public String toString() {
		return "CachedTileMessage [x=" + x + ", y=" + y + ", index=" + index + "]";
	}

}
