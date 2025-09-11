package ru.snake.remote.server.screen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageCanvas extends JPanel {

	private static final int DEFAULT_HEIGHT = 1080;

	private static final int DEFAULT_WIDTH = 1920;

	private BufferedImage buffer;

	public ImageCanvas() {
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
		g.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
	}

}
