package ru.snake.remote.core.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import ru.snake.remote.core.block.BlockDecompressor;
import ru.snake.remote.core.block.HalfChromaDecompressor;

public class ImageDecompressor {

	private final BlockDecompressor decompressor;

	public ImageDecompressor() {
		this.decompressor = new HalfChromaDecompressor();
	}

	public BufferedImage decompress(final byte[] bytes) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				DataInputStream dis = new DataInputStream(bais)) {
			int width = dis.readInt();
			int height = dis.readInt();
			byte[] block = new byte[decompressor.getLength()];
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			WritableRaster raster = image.getRaster();

			for (int y = 0; y < height; y += decompressor.getHeight()) {
				int sy = y;

				for (int x = 0; x < width; x += decompressor.getWidth()) {
					int sx = x;

					dis.readFully(block);
					decompressor.decompress(block, (dx, dy, color) -> {
						int[] components = new int[] { color.getRed(), color.getGreen(), color.getBlue() };
						raster.setPixel(sx + dx, sy + dy, components);
					});
				}
			}

			return image;
		} catch (IOException e) {
			throw new RuntimeException("Decompression error", e);
		}
	}

	@Override
	public String toString() {
		return "ImageDecompressor [decompressor=" + decompressor + "]";
	}

}
