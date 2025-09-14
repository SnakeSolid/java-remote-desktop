package ru.snake.remote.client;

import ru.snake.remote.client.screen.ScreenLoop;
import ru.snake.remote.core.block.CompressionQuality;
import ru.snake.remote.eventloop.client.ClientReceiver;
import ru.snake.remote.eventloop.client.ClientSender;

public class DefaultClient implements ClientReceiver {

	private final ClientSender sender;

	private final ScreenLoop screenLoop;

	private final RobotWrapper robot;

	public DefaultClient(final ClientSender sender, final ScreenLoop screenLoop, final RobotWrapper robot) {
		this.sender = sender;
		this.screenLoop = screenLoop;
		this.robot = robot;
	}

	@Override
	public void onClearTiles() {
		screenLoop.clearCache();
	}

	@Override
	public void onMousePress(int x, int y, int button) {
		robot.mousePress(x, y, button);
	}

	@Override
	public void onMouseRelease(int x, int y, int button) {
		robot.mouseRelease(x, y, button);
	}

	@Override
	public void onMouseMove(int x, int y) {
		robot.mouseMove(x, y);
	}

	@Override
	public void onMouseScroll(int units) {
		robot.mouseWheel(units);
	}

	@Override
	public void onKeyPress(int keycode) {
		robot.keyPress(keycode);
	}

	@Override
	public void onKeyRelease(int keycode) {
		robot.keyRelease(keycode);
	}

	@Override
	public void onChangeQuality(int quality) {
		CompressionQuality[] values = CompressionQuality.values();

		if (quality >= 0 && quality < values.length) {
			screenLoop.setQuality(values[quality]);
		}
	}

}
