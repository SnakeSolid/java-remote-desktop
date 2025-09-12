package ru.snake.remote.eventloop.message;

public class MousePressMessage {

	private int x;

	private int y;

	private int button;

	public MousePressMessage() {
		this.x = 0;
		this.y = 0;
		this.button = 0;
	}

	public MousePressMessage(final int x, final int y, final int button) {
		this.x = x;
		this.y = y;
		this.button = button;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getButton() {
		return button;
	}

	public void setButton(int button) {
		this.button = button;
	}

	@Override
	public String toString() {
		return "MousePressMessage [x=" + x + ", y=" + y + ", button=" + button + "]";
	}

}
