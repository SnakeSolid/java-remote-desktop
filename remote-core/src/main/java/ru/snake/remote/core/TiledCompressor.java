package ru.snake.remote.core;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

import ru.snake.remote.core.image.ImageCompressor;
import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;
import ru.snake.remote.core.tile.TileCache;
import ru.snake.remote.core.tile.TileResult;

public class TiledCompressor {

	private static final int DEFAULT_TILE_WIDTH = 64;

	private static final int DEFAULT_TILE_HEIGHT = 64;

	private final TileCache cache;

	private final ImageCompressor compressor;

	private final int tileWidth;

	private final int tileHeight;

	public TiledCompressor() {
		this(DEFAULT_TILE_WIDTH, DEFAULT_TILE_HEIGHT);
	}

	public TiledCompressor(final int tileWidth, final int tileHeight) {
		this.cache = new TileCache();
		this.compressor = new ImageCompressor();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	public void compress(
		final BufferedImage image,
		final Consumer<CreatedTile> createdCallback,
		final Consumer<CachedTile> cachedCallback
	) {
		int width = image.getWidth();
		int height = image.getHeight();

		for (int y = 0; y < height; y += tileHeight) {
			for (int x = 0; x < width; x += tileWidth) {
				int cropWidth = Integer.min(width - x, tileWidth);
				int cropHeight = Integer.min(height - y, tileHeight);
				BufferedImage tile = image.getSubimage(x, y, cropWidth, cropHeight);
				byte[] block = compressor.compress(tile);
				TileResult result = cache.put(block);

				if (result.isFromCache()) {
					cachedCallback.accept(new CachedTile(x, y, result.getTileIndex()));
				} else {
					createdCallback.accept(new CreatedTile(x, y, result.getTileIndex(), block));
				}
			}
		}
	}

	public void clearCache() {
		cache.clear();
	}

	@Override
	public String toString() {
		return "TiledCompressor [cache=" + cache + ", compressor=" + compressor + ", tileWidth=" + tileWidth
				+ ", tileHeight=" + tileHeight + "]";
	}

}
