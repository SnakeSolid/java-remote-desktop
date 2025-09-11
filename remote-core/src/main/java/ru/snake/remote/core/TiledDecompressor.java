package ru.snake.remote.core;

import java.awt.image.BufferedImage;

import ru.snake.remote.core.image.ImageDecompressor;
import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;
import ru.snake.remote.core.tile.TileCache;

public class TiledDecompressor {

	private static final int DEFAULT_TILE_WIDTH = 1920;

	private static final int DEFAULT_TILE_HEIGHT = 1080;

	private final TileCache cache;

	private final ImageDecompressor decompressor;

	private final BufferedImage image;

	public TiledDecompressor() {
		this(DEFAULT_TILE_WIDTH, DEFAULT_TILE_HEIGHT);
	}

	public TiledDecompressor(final int imageWidth, final int imageHeight) {
		this.cache = new TileCache();
		this.decompressor = new ImageDecompressor();
		this.image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void decompress(final CreatedTile tile) {
		int index = tile.getIndex();
		byte[] block = tile.getData();
		cache.put(index, block);

		BufferedImage subImage = decompressor.decompress(block);
		image.getGraphics().drawImage(subImage, tile.getX(), tile.getY(), null);
	}

	public void decompress(final CachedTile tile) {
		int index = tile.getIndex();
		byte[] block = cache.get(index);

		BufferedImage subImage = decompressor.decompress(block);
		image.getGraphics().drawImage(subImage, tile.getX(), tile.getY(), null);
	}

	public void clearCache() {
		cache.clear();
	}

	@Override
	public String toString() {
		return "TiledDecompressor [cache=" + cache + ", decompressor=" + decompressor + ", image=" + image + "]";
	}

}
