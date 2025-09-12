package ru.snake.remote.eventloop.message;

public class KeyReleaseMessage {

	private int keycode;

	public KeyReleaseMessage() {
		this.keycode = 0;
	}

	public KeyReleaseMessage(final int keycode) {
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
