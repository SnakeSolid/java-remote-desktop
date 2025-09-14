package ru.snake.remote.server;

import java.awt.Image;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Icons {

	private static final Map<String, ImageIcon> ICON_CACHE = new HashMap<>();

	private Icons() {
	}

	public static Image getApplicationImage() {
		URL resource256 = Icons.class.getResource("/icons/application-256x256.png");
		URL resource48 = Icons.class.getResource("/icons/application-48x48.png");
		URL resource32 = Icons.class.getResource("/icons/application-32x32.png");
		URL resource16 = Icons.class.getResource("/icons/application-16x16.png");

		try {
			BufferedImage icon256 = ImageIO.read(resource256);
			BufferedImage icon48 = ImageIO.read(resource48);
			BufferedImage icon32 = ImageIO.read(resource32);
			BufferedImage icon16 = ImageIO.read(resource16);
			BaseMultiResolutionImage image = new BaseMultiResolutionImage(icon256, icon48, icon32, icon16);

			return image;
		} catch (IOException e) {
			throw new RuntimeException("Failed to load application icon.", e);
		}
	}

	public static Icon getKeyboardEnableIcon(final boolean large) {
		return loadIcon(large ? "/icons/keyboard-enabled-32x32.png" : "/icons/keyboard-enabled-16x16.png");
	}

	public static Icon getKeyboardDisableIcon(final boolean large) {
		return loadIcon(large ? "/icons/keyboard-disabled-32x32.png" : "/icons/keyboard-disabled-16x16.png");
	}

	public static Icon getMouseEnableIcon(final boolean large) {
		return loadIcon(large ? "/icons/mouse-enabled-32x32.png" : "/icons/mouse-enabled-16x16.png");
	}

	public static Icon getMouseDisableIcon(final boolean large) {
		return loadIcon(large ? "/icons/mouse-disabled-32x32.png" : "/icons/mouse-disabled-16x16.png");
	}

	public static Icon getQualityLowIcon(final boolean large) {
		return loadIcon(large ? "/icons/codec-low-32x32.png" : "/icons/codec-low-16x16.png");
	}

	public static Icon getQualityMediumIcon(final boolean large) {
		return loadIcon(large ? "/icons/codec-medium-32x32.png" : "/icons/codec-medium-16x16.png");
	}

	public static Icon getQualityHighIcon(final boolean large) {
		return loadIcon(large ? "/icons/codec-high-32x32.png" : "/icons/codec-high-16x16.png");
	}

	private synchronized static Icon loadIcon(String path) {
		ImageIcon icon = ICON_CACHE.get(path);

		if (icon == null) {
			URL uri = Icons.class.getResource(path);
			icon = new ImageIcon(uri);

			ICON_CACHE.put(path, icon);
		}

		return icon;
	}
}
