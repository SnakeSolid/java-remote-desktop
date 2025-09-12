package ru.snake.remote.eventloop.message;

public class KeyPressMessage {

	private int keycode;

	public KeyPressMessage() {
		this.keycode = 0;
	}

	public KeyPressMessage(final int keycode) {
		this.keycode = keycode;
	}

	public int getKeycode() {
		return keycode;
	}

	public void setKeycode(int keycode) {
		this.keycode = keycode;
	}

	@Override
	public String toString() {
		return "KeyPressMessage [keycode=" + keycode + "]";
	}

}
