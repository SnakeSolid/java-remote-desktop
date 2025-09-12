package ru.snake.remote.eventloop.message;

public class MouseScrollMessage {

	private int units;

	public MouseScrollMessage() {
		this.units = 0;
	}

	public MouseScrollMessage(final int units) {
		this.units = units;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return "MouseScrollMessage [units=" + units + "]";
	}

}
