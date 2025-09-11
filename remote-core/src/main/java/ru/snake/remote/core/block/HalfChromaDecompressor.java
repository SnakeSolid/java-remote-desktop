package ru.snake.remote.core.block;

import ru.snake.remote.core.image.HSLColor;
import ru.snake.remote.core.quantizer.LinearQuantizer;
import ru.snake.remote.core.stream.InputBitStream;

public class HalfChromaDecompressor implements BlockDecompressor {

	private static final int WIDTH = 4;

	private static final int HEIGHT = 2;

	private static final int LENGTH = 3;

	private static final LinearQuantizer QUANTIZER = new LinearQuantizer();

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public int getLength() {
		return LENGTH;
	}

	@Override
	public void decompress(final byte[] buffer, final DecompressorCallback callback) {
		InputBitStream stream = new InputBitStream(buffer);

		// Left four pixels
		float luma_0_0 = QUANTIZER.dequantize(stream.read(2));
		float luma_0_1 = QUANTIZER.dequantize(stream.read(2));
		float luma_1_0 = QUANTIZER.dequantize(stream.read(2));
		float luma_1_1 = QUANTIZER.dequantize(stream.read(2));

		// Right four pixels
		float luma_0_2 = QUANTIZER.dequantize(stream.read(2));
		float luma_0_3 = QUANTIZER.dequantize(stream.read(2));
		float luma_1_2 = QUANTIZER.dequantize(stream.read(2));
		float luma_1_3 = QUANTIZER.dequantize(stream.read(2));

		// Left block chroma
		float leftHue = QUANTIZER.dequantize(stream.read(2));
		float leftSat = QUANTIZER.dequantize(stream.read(2));

		// Left block chroma
		float rightHue = QUANTIZER.dequantize(stream.read(2));
		float rightSat = QUANTIZER.dequantize(stream.read(2));

		callback.setPixel(0, 0, new HSLColor(leftHue, leftSat, luma_0_0).toRGB());
		callback.setPixel(1, 0, new HSLColor(leftHue, leftSat, luma_0_1).toRGB());
		callback.setPixel(0, 1, new HSLColor(leftHue, leftSat, luma_1_0).toRGB());
		callback.setPixel(1, 1, new HSLColor(leftHue, leftSat, luma_1_1).toRGB());

		callback.setPixel(2, 0, new HSLColor(rightHue, rightSat, luma_0_2).toRGB());
		callback.setPixel(3, 0, new HSLColor(rightHue, rightSat, luma_0_3).toRGB());
		callback.setPixel(2, 1, new HSLColor(rightHue, rightSat, luma_1_2).toRGB());
		callback.setPixel(3, 1, new HSLColor(rightHue, rightSat, luma_1_3).toRGB());
	}

}
