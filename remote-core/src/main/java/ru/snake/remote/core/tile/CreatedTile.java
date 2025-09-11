package ru.snake.remote.core.tile;

import java.util.Arrays;

public class CreatedTile implements Tile {

	private final int x;

	private final int y;

	private final int index;

	private final byte[] data;

	public CreatedTile(final int x, final int y, final int index, final byte[] data) {
		this.x = x;
		this.y = y;
		this.index = index;
		this.data = data;
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

	public byte[] getData() {
		return data;
	}

	@Override
	public String toString() {
		return "CreatedTile [x=" + x + ", y=" + y + ", index=" + index + ", data=" + Arrays.toString(data) + "]";
	}

}
