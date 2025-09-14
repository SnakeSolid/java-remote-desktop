package ru.snake.remote.server.screen;

import ru.snake.remote.core.block.CompressionQuality;
import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;

public class ScreenOperation {

	private static final ScreenOperation SCREEN_OPERATION = new ScreenOperation(null, null, null, true);

	private final CompressionQuality quality;

	private final CreatedTile createdTile;

	private final CachedTile cachedTile;

	private final boolean screenSync;

	public ScreenOperation(
		final CompressionQuality quality,
		final CreatedTile createdTile,
		final CachedTile cachedTile,
		final boolean screenSync
	) {
		this.quality = quality;
		this.createdTile = createdTile;
		this.cachedTile = cachedTile;
		this.screenSync = screenSync;
	}

	public CompressionQuality getQuality() {
		return quality;
	}

	public CreatedTile getCreatedTile() {
		return createdTile;
	}

	public CachedTile getCachedTile() {
		return cachedTile;
	}

	public boolean isScreenSync() {
		return screenSync;
	}

	@Override
	public String toString() {
		return "ScreenOperation [quality=" + quality + ", createdTile=" + createdTile + ", cachedTile=" + cachedTile
				+ ", screenSync=" + screenSync + "]";
	}

	public static ScreenOperation quality(final CompressionQuality quality) {
		return new ScreenOperation(quality, null, null, false);
	}

	public static ScreenOperation sync() {
		return SCREEN_OPERATION;
	}

}
