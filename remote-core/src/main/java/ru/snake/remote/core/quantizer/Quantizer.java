package ru.snake.remote.core.quantizer;

public interface Quantizer {

	public int getNBits();

	public int quantize(float value);

	public float dequantize(int value);

}
