package ru.snake.remote.client;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
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

	public void mousePress(int x, int y, int button) {
		robot.mouseMove(x, y);
		robot.mousePress(indexToButton(button));
	}

	public void mouseRelease(int x, int y, int button) {
		robot.mouseMove(x, y);
		robot.mouseRelease(indexToButton(button));
	}

	public void mouseMove(int x, int y) {
		robot.mouseMove(x, y);
	}

	public void mouseWheel(int units) {
		if (units != 0) {
			robot.mouseWheel(units);
		}
	}

	private int indexToButton(int button) {
		switch (button) {
		case 1:
			return InputEvent.BUTTON1_DOWN_MASK;
		case 2:
			return InputEvent.BUTTON2_DOWN_MASK;
		case 3:
			return InputEvent.BUTTON3_DOWN_MASK;
		default:
			return InputEvent.BUTTON1_DOWN_MASK;
		}
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
