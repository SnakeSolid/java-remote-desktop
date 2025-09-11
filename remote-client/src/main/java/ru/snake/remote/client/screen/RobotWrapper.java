package ru.snake.remote.client.screen;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class RobotWrapper {

	private final Toolkit toolkit;

	private final Robot robot;

	public RobotWrapper(final Robot robot) {
		this.toolkit = Toolkit.getDefaultToolkit();
		this.robot = robot;
	}

	public BufferedImage createScreenCapture() {
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);

		return robot.createScreenCapture(screenRect);
	}

	public static RobotWrapper create() {
		try {
			Robot robot = new Robot();

			return new RobotWrapper(robot);
		} catch (AWTException e) {
			throw new RuntimeException("Failed to create WAT robot", e);
		}
	}

	@Override
	public String toString() {
		return "RobotWrapper [toolkit=" + toolkit + ", robot=" + robot + "]";
	}

}
