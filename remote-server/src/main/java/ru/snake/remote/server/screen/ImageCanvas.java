package ru.snake.remote.server.screen;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageCanvas extends JPanel {

	private static final int DEFAULT_HEIGHT = 1080;

	private static final int DEFAULT_WIDTH = 1920;

	private StretchMode stretchMode;

	private BufferedImage buffer;

	public ImageCanvas() {
		stretchMode = StretchMode.STRETCH;
		buffer = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}

	public final void setImageSize(final int width, final int height) {
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	public final void setImageTile(final int x, final int y, final BufferedImage tile) {
		buffer.getGraphics().drawImage(tile, x, y, null);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		int canvasWidth = getWidth();
		int canvasHeight = getHeight();
		int imageWidth = buffer.getWidth();
		int imageHeight = buffer.getHeight();

		switch (stretchMode) {
		case STRETCH:
			g.drawImage(buffer, 0, 0, canvasWidth, canvasHeight, null);
			break;

		case FIT_SIZE:
			break;

		case KEEP_SIZE:
			g.drawImage(buffer, (canvasWidth - imageWidth) / 2, (canvasHeight - imageHeight) / 2, null);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + stretchMode);
		}
	}

	public static void main(String[] args) {
		int canvasWidth = 200;
		int imageWidth = 100;
		int value = -250;
		int result = Integer.max(0, Integer.min(Math.round((float) value * imageWidth / canvasWidth), imageWidth - 1));

		System.out.println(result);
	}

	public Point localToImage(int x, int y) {
		int canvasWidth = getWidth();
		int canvasHeight = getHeight();
		int imageWidth = buffer.getWidth();
		int imageHeight = buffer.getHeight();

		switch (stretchMode) {
		case STRETCH:
			return new Point(
				Integer.max(0, Integer.min(Math.round((float) x * imageWidth / canvasWidth), imageWidth - 1)),
				Integer.max(0, Integer.min(Math.round((float) y * imageHeight / canvasHeight), imageHeight - 1))
			);

		case FIT_SIZE:
			throw new IllegalArgumentException("Unimplemented!");

		case KEEP_SIZE:
			return new Point(
				Integer.max(0, Integer.min(x + (canvasWidth - imageWidth) / 2, imageWidth - 1)),
				Integer.max(0, Integer.min(y + (canvasHeight - imageHeight) / 2, imageHeight - 1))
			);

		default:
			throw new IllegalArgumentException("Unexpected value: " + stretchMode);
		}
	}

}
