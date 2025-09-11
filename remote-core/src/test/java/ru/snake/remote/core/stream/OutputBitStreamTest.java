package ru.snake.remote.core.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OutputBitStreamTest {

	@Test
	public void mustBeEmptyWhenNoWrites() {
		OutputBitStream stream = new OutputBitStream();
		byte[] data = stream.getData();

		Assertions.assertArrayEquals(new byte[] {}, data);
	}

	@Test
	public void mustContainValueWhenValueWritten() {
		OutputBitStream stream = new OutputBitStream();
		stream.write(1, 1);
		byte[] data = stream.getData();

		Assertions.assertArrayEquals(new byte[] { (byte) 0b1000_0000 }, data);
	}

	@Test
	public void mustContainValuesWhenValuesWritten() {
		OutputBitStream stream = new OutputBitStream();
		stream.write(3, 4);
		stream.write(3, 4);
		byte[] data = stream.getData();

		Assertions.assertArrayEquals(new byte[] { (byte) 0b0011_0011 }, data);
	}

	@Test
	public void mustContainLargeValueWhenValueWritten() {
		OutputBitStream stream = new OutputBitStream();
		stream.write(0x12_3a_bc, 24);
		byte[] data = stream.getData();

		Assertions.assertArrayEquals(new byte[] { 0x12, 0x3a, (byte) 0xbc }, data);
	}

	@Test
	public void mustContainLargeValuesWhenValuesWritten() {
		OutputBitStream stream = new OutputBitStream();
		stream.write(0x123, 12);
		stream.write(0x1234, 16);
		stream.write(0x12345, 20);
		byte[] data = stream.getData();

		Assertions.assertArrayEquals(new byte[] { 0x12, 0x31, 0x23, 0x41, 0x23, 0x45 }, data);
	}

}
