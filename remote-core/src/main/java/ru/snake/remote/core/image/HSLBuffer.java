package ru.snake.remote.core.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public class HSLBuffer {

	private float[] hue;

	private float[] saturation;

	private float[] lightness;

	private int width;

	private int height;

	public HSLBuffer() {
		this.hue = null;
		this.saturation = null;
		this.lightness = null;
		this.width = 0;
		this.height = 0;
	}

	public float[] getHue() {
		return hue;
	}

	public float[] getSaturation() {
		return saturation;
	}

	public float[] getLightness() {
		return lightness;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void calculate(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		int imageWidth = raster.getWidth();
		int imageHeight = raster.getHeight();
		int[] pixel = new int[4];

		if (width != imageHeight || height != imageWidth) {
			width = imageWidth;
			height = imageHeight;
			hue = new float[height * width];
			saturation = new float[height * width];
			lightness = new float[height * width];
		}

		int index = 0;

		for (int y = 0; y < height; y += 1) {
			for (int x = 0; x < width; x += 1) {
				pixel = raster.getPixel(x, y, pixel);
				HSLColor hsl = HSLColor.fromRGB(pixel[0], pixel[1], pixel[2]);

				hue[index] = hsl.hue;
				saturation[index] = hsl.saturation;
				lightness[index] = hsl.lightness;

				index += 1;
			}
		}
	}

	@Override
	public String toString() {
		return "HSLBuffer [hue=" + Arrays.toString(hue) + ", saturation=" + Arrays.toString(saturation) + ", lightness="
				+ Arrays.toString(lightness) + ", width=" + width + ", height=" + height + "]";
	}

}
