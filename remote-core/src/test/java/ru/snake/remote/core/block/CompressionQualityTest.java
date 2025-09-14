package ru.snake.remote.core.block;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

import ru.snake.remote.core.image.HSLBuffer;

public class CompressionQualityTest {

	@Test
	public void mustCompressLowQuality() {
		BlockCompressor comressor = BlockCompressorFactory.forQuality(CompressionQuality.LOW);
		BlockDecompressor decomressor = BlockDecompressorFactory.forQuality(CompressionQuality.LOW);
		HSLBuffer buffer = new HSLBuffer();
		buffer.calculate(new BufferedImage(4, 2, BufferedImage.TYPE_INT_RGB));

		byte[] data = comressor.compress(0, 0, buffer);
		decomressor.decompress(data, (x, y, c) -> {
		});

		assertEquals(data.length, decomressor.getLength());
	}

	@Test
	public void mustCompressMediumQuality() {
		BlockCompressor comressor = BlockCompressorFactory.forQuality(CompressionQuality.MEDIUM);
		BlockDecompressor decomressor = BlockDecompressorFactory.forQuality(CompressionQuality.MEDIUM);
		HSLBuffer buffer = new HSLBuffer();
		buffer.calculate(new BufferedImage(4, 2, BufferedImage.TYPE_INT_RGB));

		byte[] data = comressor.compress(0, 0, buffer);
		decomressor.decompress(data, (x, y, c) -> {
		});

		assertEquals(data.length, decomressor.getLength());
	}

	@Test
	public void mustCompressHighQuality() {
		BlockCompressor comressor = BlockCompressorFactory.forQuality(CompressionQuality.HIGH);
		BlockDecompressor decomressor = BlockDecompressorFactory.forQuality(CompressionQuality.HIGH);
		HSLBuffer buffer = new HSLBuffer();
		buffer.calculate(new BufferedImage(4, 2, BufferedImage.TYPE_INT_RGB));

		byte[] data = comressor.compress(0, 0, buffer);
		decomressor.decompress(data, (x, y, c) -> {
		});

		assertEquals(data.length, decomressor.getLength());
	}

}
