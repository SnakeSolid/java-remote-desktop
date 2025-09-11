package ru.snake.remote.core.tile;

public class TileResult {

	private final boolean fromCache;

	private final int tileIndex;

	public TileResult(boolean fromCache, int tileIndex) {
		this.fromCache = fromCache;
		this.tileIndex = tileIndex;
	}

	public boolean isFromCache() {
		return fromCache;
	}

	public int getTileIndex() {
		return tileIndex;
	}

	@Override
	public String toString() {
		return "TileResult [fromCache=" + fromCache + ", tileIndex=" + tileIndex + "]";
	}

}
