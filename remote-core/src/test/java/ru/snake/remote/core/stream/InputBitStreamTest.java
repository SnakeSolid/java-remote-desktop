package ru.snake.remote.core.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InputBitStreamTest {

	@Test
	public void mustBeEmptyWhenNoRead() {
		InputBitStream stream = new InputBitStream(new byte[] {});

		Assertions.assertThrows(RuntimeException.class, () -> stream.read(1));
	}

	@Test
	public void mustContainValueWhenValueRead() {
		InputBitStream stream = new InputBitStream(new byte[] { (byte) 0x80 });

		Assertions.assertEquals(1, stream.read(1));
	}

	@Test
	public void mustContainValuesWhenValuesRead() {
		InputBitStream stream = new InputBitStream(new byte[] { 0b0011_0011 });

		Assertions.assertEquals(3, stream.read(4));
		Assertions.assertEquals(3, stream.read(4));
	}

	@Test
	public void mustContainLargeValueWhenValueRead() {
		InputBitStream stream = new InputBitStream(new byte[] { 0x12, 0x3a, (byte) 0xbc });

		Assertions.assertEquals(0x12_3a_bc, stream.read(24));
	}

	@Test
	public void mustContainLargeValuesWhenValuesRead() {
		InputBitStream stream = new InputBitStream(new byte[] { 0x12, 0x31, 0x23, 0x41, 0x23, 0x45 });

		Assertions.assertEquals(0x123, stream.read(12));
		Assertions.assertEquals(0x1234, stream.read(16));
		Assertions.assertEquals(0x12345, stream.read(20));
	}

}
