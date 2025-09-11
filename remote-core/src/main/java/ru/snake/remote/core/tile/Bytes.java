package ru.snake.remote.core.tile;

import java.util.Arrays;

public class Bytes {

	private final byte[] bytes;

	private int hashValue;

	public Bytes(byte[] bytes) {
		this.bytes = bytes;
		this.hashValue = Arrays.hashCode(bytes);
	}

	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public int hashCode() {
		return hashValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Bytes other = (Bytes) obj;

		return hashValue == other.hashValue && Arrays.equals(bytes, other.bytes);
	}

}
