package ru.snake.remote.eventloop.message;

public class ClipboardTextMessage {

	private String text;

	public ClipboardTextMessage() {
		this.text = "";
	}

	public ClipboardTextMessage(final String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "ClipboardTextMessage [text=" + text + "]";
	}

}
