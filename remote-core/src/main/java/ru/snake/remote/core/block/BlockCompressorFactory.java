package ru.snake.remote.core.block;

public class BlockCompressorFactory {

	private static final BlockCompressor QUALITY_LOW = new HalfChromaCompressor(2, 2);

	private static final BlockCompressor QUALITY_MEDIUM = new HalfChromaCompressor(4, 2);

	private static final BlockCompressor QUALITY_HIGH = new HalfChromaCompressor(4, 4);

	public static BlockCompressor forQuality(final CompressionQuality quality) {
		switch (quality) {
		case LOW:
			return QUALITY_LOW;

		case MEDIUM:
			return QUALITY_MEDIUM;

		case HIGH:
			return QUALITY_HIGH;

		default:
			throw new IllegalArgumentException("Unexpected value: " + quality);
		}
	}

}
