package ru.snake.remote.core.stream;

import java.io.ByteArrayOutputStream;

public class OutputBitStream {

	private final ByteArrayOutputStream stream;

	private int currentValue;

	private int currentBit;

	public OutputBitStream() {
		this.stream = new ByteArrayOutputStream();
		this.currentValue = 0;
		this.currentBit = 7;
	}

	public OutputBitStream(int length) {
		this.stream = new ByteArrayOutputStream(length);
		this.currentValue = 0;
		this.currentBit = 7;
	}

	public void clear() {
		currentValue = 0;
		currentBit = 7;
		stream.reset();
	}

	public void write(int value, int bitCount) {
		for (int bitIndex = bitCount - 1; bitIndex >= 0; bitIndex -= 1) {
			writeBit((value >> bitIndex) & 1);
		}
	}

	private void writeBit(int bit) {
		if (currentBit >= 0) {
			currentValue |= (bit & 0x01) << currentBit;
			currentBit -= 1;
		} else {
			stream.write(currentValue);
			currentBit = 7;
			currentValue = (bit & 0x01) << currentBit;
			currentBit -= 1;
		}
	}

	public byte[] getData() {
		if (currentBit < 7) {
			stream.write(currentValue);
		}

		return stream.toByteArray();
	}

	@Override
	public String toString() {
		return "BitStream [stream=" + stream + "]";
	}

}
