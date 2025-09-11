package ru.snake.remote.core.block;

import ru.snake.remote.core.image.HSLBuffer;
import ru.snake.remote.core.quantizer.LinearQuantizer;
import ru.snake.remote.core.stream.OutputBitStream;

public class HalfChromaCompressor implements BlockCompressor {

	private static final int WIDTH = 4;

	private static final int HEIGHT = 2;

	private static final int LENGTH = 3;

	private static final LinearQuantizer QUANTIZER = new LinearQuantizer();

	private final OutputBitStream stream;

	public HalfChromaCompressor() {
		this.stream = new OutputBitStream(LENGTH);
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
		return LENGTH;
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
		stream.write(QUANTIZER.quantize(lightness[offset + 0]), 2);
		stream.write(QUANTIZER.quantize(lightness[offset + 1]), 2);
		stream.write(QUANTIZER.quantize(lightness[offset + width + 0]), 2);
		stream.write(QUANTIZER.quantize(lightness[offset + width + 1]), 2);

		// Right four pixels
		stream.write(QUANTIZER.quantize(lightness[offset + 2]), 2);
		stream.write(QUANTIZER.quantize(lightness[offset + 3]), 2);
		stream.write(QUANTIZER.quantize(lightness[offset + width + 2]), 2);
		stream.write(QUANTIZER.quantize(lightness[offset + width + 3]), 2);

		// Left block hue
		float leftHue = (hue[offset + 0] + hue[offset + 1] + hue[offset + width + 0] + hue[offset + width + 1]) / 4.0f;
		stream.write(QUANTIZER.quantize(leftHue), 2);

		// Left block saturation
		float leftSat = (saturation[offset + 0] + saturation[offset + 1] + saturation[offset + width + 0]
				+ saturation[offset + width + 1]) / 4.0f;
		stream.write(QUANTIZER.quantize(leftSat), 2);

		// Right block hue
		float rightHue = (hue[offset + 2] + hue[offset + 3] + hue[offset + width + 2] + hue[offset + width + 3]) / 4.0f;
		stream.write(QUANTIZER.quantize(rightHue), 2);

		// Right block saturation
		float rightSat = (saturation[offset + 2] + saturation[offset + 3] + saturation[offset + width + 2]
				+ saturation[offset + width + 3]) / 4.0f;
		stream.write(QUANTIZER.quantize(rightSat), 2);

		return stream.getData();
	}

	@Override
	public String toString() {
		return "HalfChromaCompressor [stream=" + stream + "]";
	}

}
