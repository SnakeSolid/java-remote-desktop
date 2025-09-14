package ru.snake.remote.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import ru.snake.remote.core.block.BlockCompressorFactory;
import ru.snake.remote.core.block.BlockDecompressorFactory;
import ru.snake.remote.core.block.CompressionQuality;
import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;

public class Main {

	public static void main(String[] args) throws IOException {
		TiledCompressor compressor = new TiledCompressor();
		TiledDecompressor decompressor = new TiledDecompressor();

		compressor.setCompressor(BlockCompressorFactory.forQuality(CompressionQuality.MEDIUM));
		decompressor.setDecompressor(BlockDecompressorFactory.forQuality(CompressionQuality.MEDIUM));

		for (int index = 1; index < args.length; index += 1) {
			List<CreatedTile> createdTiles = new ArrayList<>();
			List<CachedTile> cachedTiles = new ArrayList<>();

			BufferedImage sourceImage = ImageIO.read(new File(args[index]));
			compressor.compress(sourceImage, createdTiles::add, cachedTiles::add);
			decompressor.setImageSize(sourceImage.getWidth(), sourceImage.getHeight());
			createdTiles.forEach(decompressor::decompress);
			cachedTiles.forEach(decompressor::decompress);

			BufferedImage resultImage = decompressor.getImage();
			ImageIO.write(resultImage, "png", new File(args[0]));
		}
	}

}
