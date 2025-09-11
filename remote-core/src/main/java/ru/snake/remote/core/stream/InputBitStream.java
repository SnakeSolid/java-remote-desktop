package ru.snake.remote.core.stream;

import java.io.ByteArrayInputStream;

public class InputBitStream {

	private final ByteArrayInputStream stream;

	private int currentValue;

	private int currentBit;

	public InputBitStream(byte[] data) {
		this.stream = new ByteArrayInputStream(data);
		this.currentValue = 0;
		this.currentBit = -1;
	}

	public int read(int bitCount) {
		int value = 0;

		for (int bitIndex = bitCount - 1; bitIndex >= 0; bitIndex -= 1) {
			value |= readBit() << bitIndex;
		}

		return value;
	}

	private int readBit() {
		int result;

		if (currentBit >= 0) {
			result = (currentValue >> currentBit) & 1;
			currentBit -= 1;
		} else {
			currentValue = stream.read();

			if (currentValue == -1) {
				throw new RuntimeException("End of data");
			}

			currentBit = 7;
			result = (currentValue >> currentBit) & 1;
			currentBit -= 1;
		}

		return result;
	}

	@Override
	public String toString() {
		return "BitStream [stream=" + stream + "]";
	}

}
