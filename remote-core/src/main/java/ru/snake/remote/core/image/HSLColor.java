package ru.snake.remote.core.image;

import java.awt.Color;

public class HSLColor {

	public final float hue;

	public final float saturation;

	public final float lightness;

	public HSLColor(final float hue, final float saturation, final float lightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.lightness = lightness;
	}

	public Color toRGB() {
		float chroma = (1 - Math.abs(2 * lightness - 1)) * saturation;
		float x = chroma * (1 - Math.abs((hue * 6) % 2 - 1));
		float m = lightness - chroma / 2.0f;
		float red, green, blue;

		if (hue < 1 / 6.0f) {
			red = chroma;
			green = x;
			blue = 0;
		} else if (hue < 2 / 6.0f) {
			red = x;
			green = chroma;
			blue = 0;
		} else if (hue < 3 / 6.0f) {
			red = 0;
			green = chroma;
			blue = x;
		} else if (hue < 4 / 6.0f) {
			red = 0;
			green = x;
			blue = chroma;
		} else if (hue < 5 / 6.0f) {
			red = x;
			green = 0;
			blue = chroma;
		} else {
			red = chroma;
			green = 0;
			blue = x;
		}

		red += m;
		green += m;
		blue += m;

		return new Color((int) (red * 255 + 0.5), (int) (green * 255 + 0.5), (int) (blue * 255 + 0.5));
	}

	@Override
	public String toString() {
		return "HSLColor [hue=" + hue + ", saturation=" + saturation + ", lightness=" + lightness + "]";
	}

	public static HSLColor fromRGB(final int r, final int g, final int b) {
		return fromRGB(r / 255.0f, g / 255.0f, b / 255.0f);
	}

	public static HSLColor fromRGB(final float r, final float g, final float b) {
		float max = Math.max(r, Math.max(g, b));
		float min = Math.min(r, Math.min(g, b));
		float delta = max - min;

		float h = 0.0f;
		float s = 0.0f;
		float l = (max + min) / 2.0f;

		if (delta != 0) {
			s = (l > 0.5f) ? delta / (2f - max - min) : delta / (max + min);

			if (max == r) {
				h = (g - b) / delta % 6.0f;
			} else if (max == g) {
				h = (b - r) / delta + 2.0f;
			} else {
				h = (r - g) / delta + 4.0f;
			}

			h /= 6.0f;
		}

		return new HSLColor(h, s, l);
	}

}
