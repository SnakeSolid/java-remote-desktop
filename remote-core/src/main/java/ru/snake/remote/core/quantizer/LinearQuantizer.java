package ru.snake.remote.core.quantizer;

public class LinearQuantizer implements Quantizer {

	private static final int N_BITS = 2;

	private static final double EPSILON = 1e-6;

	@Override
	public int getNBits() {
		return N_BITS;
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
