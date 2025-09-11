package ru.snake.remote.core.tile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TileCache {

	private static final int DEFAULT_SIZE = 1024;

	private final Map<Bytes, Integer> map;

	private final Bytes[] list;

	private final int listSize;

	private int nextIndex;

	public TileCache() {
		this(DEFAULT_SIZE);
	}

	public TileCache(final int size) {
		this.map = new HashMap<>();
		this.list = new Bytes[size];
		this.listSize = size;
		this.nextIndex = 0;
	}

	public byte[] get(int index) {
		Bytes bytes = list[index];

		if (bytes != null) {
			return bytes.getBytes();
		} else {
			return null;
		}
	}

	public TileResult put(final byte[] data) {
		Bytes bytes = new Bytes(data);
		Integer tileIndex = map.get(bytes);

		if (tileIndex != null) {
			return new TileResult(true, tileIndex);
		}

		map.put(bytes, nextIndex);
		tileIndex = nextIndex;
		nextIndex = (nextIndex + 1) % listSize;

		return new TileResult(false, tileIndex);
	}

	public void put(int index, byte[] data) {
		Bytes bytes = new Bytes(data);
		map.put(bytes, index);
		list[index] = bytes;
	}

	public void clear() {
		this.map.clear();
		this.nextIndex = 0;
	}

	@Override
	public String toString() {
		return "TileCache [map=" + map + ", list=" + Arrays.toString(list) + "]";
	}

}
