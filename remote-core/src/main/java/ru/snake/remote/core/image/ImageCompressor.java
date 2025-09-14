package ru.snake.remote.core.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.remote.core.block.BlockCompressor;
import ru.snake.remote.core.block.BlockCompressorFactory;
import ru.snake.remote.core.block.CompressionQuality;

public class ImageCompressor {

	private final HSLBuffer hslBuffer;

	private BlockCompressor compressor;

	public ImageCompressor() {
		this.hslBuffer = new HSLBuffer();
		this.compressor = BlockCompressorFactory.forQuality(CompressionQuality.LOW);
	}

	public void setCompressor(BlockCompressor compressor) {
		this.compressor = compressor;
	}

	public byte[] compress(final BufferedImage image) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(baos)) {
			int blockWidth = compressor.getWidth();
			int blockHeight = compressor.getHeight();
			int width = image.getWidth() / blockWidth * blockWidth;
			int height = image.getHeight() / blockHeight * blockHeight;
			hslBuffer.calculate(image);

			dos.writeInt(width);
			dos.writeInt(height);

			for (int y = 0; y < height; y += blockHeight) {
				for (int x = 0; x < width; x += blockWidth) {
					byte[] block = compressor.compress(x, y, hslBuffer);

					dos.write(block);
				}
			}

			dos.close();

			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("Compression error", e);
		}
	}

	@Override
	public String toString() {
		return "ImageCompressor [hslBuffer=" + hslBuffer + ", compressor=" + compressor + "]";
	}

}
