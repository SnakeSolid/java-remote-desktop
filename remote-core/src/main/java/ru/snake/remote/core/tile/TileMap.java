package ru.snake.remote.core.tile;

import java.util.HashMap;
import java.util.Map;

public class TileMap {

	private static final int DEFAULT_SIZE = 200;

	private final Map<Integer, Bytes> map;

	public TileMap() {
		this(DEFAULT_SIZE);
	}

	public TileMap(final int size) {
		this.map = new HashMap<>(size);
	}

	public byte[] get(int index) {
		Bytes bytes = map.get(index);

		if (bytes != null) {
			return bytes.getBytes();
		} else {
			return null;
		}
	}

	public void put(final int index, final byte[] data) {
		Bytes bytes = new Bytes(data);
		map.put(index, bytes);
	}

	public void clear() {
		this.map.clear();
	}

	@Override
	public String toString() {
		return "TileMap [map=" + map + "]";
	}

}
