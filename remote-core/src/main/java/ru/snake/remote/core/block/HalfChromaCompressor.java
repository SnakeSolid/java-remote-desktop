package ru.snake.remote.core.block;

import ru.snake.remote.core.image.HSLBuffer;
import ru.snake.remote.core.quantizer.LinearQuantizer;
import ru.snake.remote.core.quantizer.Quantizer;
import ru.snake.remote.core.stream.OutputBitStream;

public class HalfChromaCompressor implements BlockCompressor {

	private static final int WIDTH = 4;

	private static final int HEIGHT = 2;

	private final Quantizer lumaQuantizer;

	private final Quantizer chromaQuantizer;

	private final OutputBitStream stream;

	public HalfChromaCompressor(final int lumaBits, final int chromaBits) {
		this.lumaQuantizer = new LinearQuantizer(lumaBits);
		this.chromaQuantizer = new LinearQuantizer(chromaBits);
		this.stream = new OutputBitStream(lumaBits * (WIDTH * HEIGHT) / 8 + 2 * chromaBits * 2 / 8);
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
	public byte[] compress(final int x, final int y, final HSLBuffer buffer) {
		int width = buffer.getWidth();
		float[] hue = buffer.getHue();
		float[] saturation = buffer.getSaturation();
		float[] lightness = buffer.getLightness();
		int offset = x + y * width;

		stream.clear();

		// Left four pixels
		stream.write(lumaQuantizer.quantize(lightness[offset + 0]), lumaQuantizer.getNBits());
		stream.write(lumaQuantizer.quantize(lightness[offset + 1]), lumaQuantizer.getNBits());
		stream.write(lumaQuantizer.quantize(lightness[offset + width + 0]), lumaQuantizer.getNBits());
		stream.write(lumaQuantizer.quantize(lightness[offset + width + 1]), lumaQuantizer.getNBits());

		// Right four pixels
		stream.write(lumaQuantizer.quantize(lightness[offset + 2]), lumaQuantizer.getNBits());
		stream.write(lumaQuantizer.quantize(lightness[offset + 3]), lumaQuantizer.getNBits());
		stream.write(lumaQuantizer.quantize(lightness[offset + width + 2]), lumaQuantizer.getNBits());
		stream.write(lumaQuantizer.quantize(lightness[offset + width + 3]), lumaQuantizer.getNBits());

		// Left block hue
		float leftHue = (hue[offset + 0] + hue[offset + 1] + hue[offset + width + 0] + hue[offset + width + 1]) / 4.0f;
		stream.write(chromaQuantizer.quantize(leftHue), chromaQuantizer.getNBits());

		// Left block saturation
		float leftSat = (saturation[offset + 0] + saturation[offset + 1] + saturation[offset + width + 0]
				+ saturation[offset + width + 1]) / 4.0f;
		stream.write(chromaQuantizer.quantize(leftSat), chromaQuantizer.getNBits());

		// Right block hue
		float rightHue = (hue[offset + 2] + hue[offset + 3] + hue[offset + width + 2] + hue[offset + width + 3]) / 4.0f;
		stream.write(chromaQuantizer.quantize(rightHue), chromaQuantizer.getNBits());

		// Right block saturation
		float rightSat = (saturation[offset + 2] + saturation[offset + 3] + saturation[offset + width + 2]
				+ saturation[offset + width + 3]) / 4.0f;
		stream.write(chromaQuantizer.quantize(rightSat), chromaQuantizer.getNBits());

		return stream.getData();
	}

	@Override
	public String toString() {
		return "HalfChromaCompressor [lumaQuantizer=" + lumaQuantizer + ", chromaQuantizer=" + chromaQuantizer
				+ ", stream=" + stream + "]";
	}

}
