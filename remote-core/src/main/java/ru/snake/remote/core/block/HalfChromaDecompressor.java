package ru.snake.remote.core.block;

import ru.snake.remote.core.image.HSLColor;
import ru.snake.remote.core.quantizer.LinearQuantizer;
import ru.snake.remote.core.quantizer.Quantizer;
import ru.snake.remote.core.stream.InputBitStream;

public class HalfChromaDecompressor implements BlockDecompressor {

	private static final int WIDTH = 4;

	private static final int HEIGHT = 2;

	private final Quantizer lumaQuantizer;

	private final Quantizer chromaQuantizer;

	public HalfChromaDecompressor(final int lumaBits, final int chromaBits) {
		this.lumaQuantizer = new LinearQuantizer(lumaBits);
		this.chromaQuantizer = new LinearQuantizer(chromaBits);
	}

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
		return lumaQuantizer.getNBits() * (WIDTH * HEIGHT) / 8 + 2 * chromaQuantizer.getNBits() * 2 / 8;
	}

	@Override
	public void decompress(final byte[] buffer, final BlockDecompressorCallback callback) {
		InputBitStream stream = new InputBitStream(buffer);
		int lumaBits = lumaQuantizer.getNBits();
		int chromaBits = chromaQuantizer.getNBits();

		// Left four pixels
		float luma_0_0 = lumaQuantizer.dequantize(stream.read(lumaBits));
		float luma_0_1 = lumaQuantizer.dequantize(stream.read(lumaBits));
		float luma_1_0 = lumaQuantizer.dequantize(stream.read(lumaBits));
		float luma_1_1 = lumaQuantizer.dequantize(stream.read(lumaBits));

		// Right four pixels
		float luma_0_2 = lumaQuantizer.dequantize(stream.read(lumaBits));
		float luma_0_3 = lumaQuantizer.dequantize(stream.read(lumaBits));
		float luma_1_2 = lumaQuantizer.dequantize(stream.read(lumaBits));
		float luma_1_3 = lumaQuantizer.dequantize(stream.read(lumaBits));

		// Left block chroma
		float leftHue = chromaQuantizer.dequantize(stream.read(chromaBits));
		float leftSat = chromaQuantizer.dequantize(stream.read(chromaBits));

		// Left block chroma
		float rightHue = chromaQuantizer.dequantize(stream.read(chromaBits));
		float rightSat = chromaQuantizer.dequantize(stream.read(chromaBits));

		callback.setPixel(0, 0, new HSLColor(leftHue, leftSat, luma_0_0).toRGB());
		callback.setPixel(1, 0, new HSLColor(leftHue, leftSat, luma_0_1).toRGB());
		callback.setPixel(0, 1, new HSLColor(leftHue, leftSat, luma_1_0).toRGB());
		callback.setPixel(1, 1, new HSLColor(leftHue, leftSat, luma_1_1).toRGB());

		callback.setPixel(2, 0, new HSLColor(rightHue, rightSat, luma_0_2).toRGB());
		callback.setPixel(3, 0, new HSLColor(rightHue, rightSat, luma_0_3).toRGB());
		callback.setPixel(2, 1, new HSLColor(rightHue, rightSat, luma_1_2).toRGB());
		callback.setPixel(3, 1, new HSLColor(rightHue, rightSat, luma_1_3).toRGB());
	}

	@Override
	public String toString() {
		return "Half4L2CDecompressor [lumaQuantizer=" + lumaQuantizer + ", chromaQuantizer=" + chromaQuantizer + "]";
	}

}
