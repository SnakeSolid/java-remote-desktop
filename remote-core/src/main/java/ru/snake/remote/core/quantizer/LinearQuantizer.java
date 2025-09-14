package ru.snake.remote.core.quantizer;

public class LinearQuantizer implements Quantizer {

	private static final double EPSILON = 1e-6;

	private final int nBits;

	private final float upperBound;

	private final float upperBoundFactor;

	public LinearQuantizer(final int nBits) {
		this.nBits = nBits;
		this.upperBound = 1 << nBits;
		this.upperBoundFactor = 1.0f / ((1 << nBits) - 1.0f);
	}

	@Override
	public int getNBits() {
		return nBits;
	}

	@Override
	public int quantize(final float value) {
		return (int) (value * upperBound - EPSILON);
	}

	@Override
	public float dequantize(final int value) {
		return value * upperBoundFactor;
	}

}
