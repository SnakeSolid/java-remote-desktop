package ru.snake.remote.core.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HSLColorTest {

	@Test
	public void mustEncodeBlack() {
		HSLColor color = HSLColor.fromRGB(0.0f, 0.0f, 0.0f);

		Assertions.assertEquals(0.0f, color.hue);
		Assertions.assertEquals(0.0f, color.saturation);
		Assertions.assertEquals(0.0f, color.lightness);
	}

	@Test
	public void mustEncodeWhite() {
		HSLColor color = HSLColor.fromRGB(1.0f, 1.0f, 1.0f);

		Assertions.assertEquals(0.0f, color.hue);
		Assertions.assertEquals(0.0f, color.saturation);
		Assertions.assertEquals(1.0f, color.lightness);
	}

	@Test
	public void mustEncodeOrange() {
		HSLColor color = HSLColor.fromRGB(0xff, 0x81, 0x1f);

		Assertions.assertEquals(0.072916664f, color.hue);
		Assertions.assertEquals(1.0f, color.saturation);
		Assertions.assertEquals(0.56078434f, color.lightness);
	}

	@Test
	public void mustEncodeRed() {
		HSLColor color = HSLColor.fromRGB(0x7f, 0x00, 0x55);

		Assertions.assertEquals(0.8884514f, color.hue);
		Assertions.assertEquals(1.0f, color.saturation);
		Assertions.assertEquals(0.24901961f, color.lightness);
	}

}
