package ru.snake.remote.core.tile;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TileCache {

	private static final int DEFAULT_SIZE = 1024;

	private final Map<Bytes, Integer> indexMap;

	private final int capacity;

	public TileCache() {
		this(DEFAULT_SIZE);
	}

	public TileCache(final int capacity) {
		this.indexMap = new LinkedHashMap<>();
		this.capacity = capacity;
	}

	public TileResult put(final byte[] data) {
		Bytes bytes = new Bytes(data);
		Integer tileIndex = indexMap.get(bytes);

		if (tileIndex != null) {
			// Move value to top
			indexMap.remove(bytes);
			indexMap.put(bytes, tileIndex);

			return new TileResult(true, tileIndex);
		}

		if (indexMap.size() < capacity) {
			tileIndex = indexMap.size();
		} else {
			// Remove most oldest value
			Iterator<Entry<Bytes, Integer>> it = indexMap.entrySet().iterator();
			tileIndex = it.next().getValue();
			it.remove();
		}

		indexMap.put(bytes, tileIndex);

		return new TileResult(false, tileIndex);
	}

	public void clear() {
		this.indexMap.clear();
	}

	@Override
	public String toString() {
		return "TileCache [indexMap=" + indexMap + ", capacity=" + capacity + "]";
	}

}
