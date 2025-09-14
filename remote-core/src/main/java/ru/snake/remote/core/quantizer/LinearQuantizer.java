package ru.snake.remote.core.quantizer;

public class LinearQuantizer implements Quantizer {

	private static final double EPSILON = 1e-6;

	private final int nBits;

	public LinearQuantizer(final int nBits) {
		this.nBits = nBits;
	}

	@Override
	public int getNBits() {
		return nBits;
	}

	@Override
	public int quantize(final float value) {
		return (int) (value * 4.0f - EPSILON) & 3;
	}

	@Override
	public float dequantize(final int value) {
		return value / 3.0f;
	}

}
