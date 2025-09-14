package ru.snake.remote.core.block;

public class BlockDecompressorFactory {

	private static final BlockDecompressor QUALITY_LOW = new HalfChromaDecompressor(2, 2);

	private static final BlockDecompressor QUALITY_MEDIUM = new HalfChromaDecompressor(4, 2);

	private static final BlockDecompressor QUALITY_HIGH = new HalfChromaDecompressor(4, 4);

	public static BlockDecompressor forQuality(final CompressionQuality quality) {
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
