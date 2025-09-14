package ru.snake.remote.core.block;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HalfChromaDecompressorTest {

	@Test
	public void mustReturn2ForLowQuality() {
		HalfChromaDecompressor decompressor = new HalfChromaDecompressor(2, 2);

		assertEquals(3, decompressor.getLength());
	}

	@Test
	public void mustReturn2ForMediumQuality() {
		HalfChromaDecompressor decompressor = new HalfChromaDecompressor(4, 2);

		assertEquals(5, decompressor.getLength());
	}

	@Test
	public void mustReturn2ForHighQuality() {
		HalfChromaDecompressor decompressor = new HalfChromaDecompressor(4, 4);

		assertEquals(6, decompressor.getLength());
	}

}
