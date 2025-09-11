package ru.snake.remote.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;

public class Main {

	public static void main(String[] args) throws IOException {
		TiledCompressor compressor = new TiledCompressor();
		TiledDecompressor decompressor = new TiledDecompressor();
		List<CreatedTile> createdTiles = new ArrayList<>();
		List<CachedTile> cachedTiles = new ArrayList<>();
		BufferedImage sourceImage = ImageIO.read(new File(args[0]));
		compressor.compress(sourceImage, createdTiles::add, cachedTiles::add);
		createdTiles.forEach(decompressor::decompress);
		cachedTiles.forEach(decompressor::decompress);
		BufferedImage resultImage = decompressor.getImage();
		ImageIO.write(resultImage, "png", new File(args[1]));
	}

}
