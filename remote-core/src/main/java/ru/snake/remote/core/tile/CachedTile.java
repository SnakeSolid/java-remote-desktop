package ru.snake.remote.core.tile;

public class CachedTile implements Tile {

	private final int x;

	private final int y;

	private final int index;

	public CachedTile(final int x, final int y, final int index) {
		this.x = x;
		this.y = y;
		this.index = index;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "CachedTile [x=" + x + ", y=" + y + ", index=" + index + "]";
	}

}
