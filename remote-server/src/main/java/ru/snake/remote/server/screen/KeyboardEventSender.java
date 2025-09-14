package ru.snake.remote.server.screen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ru.snake.remote.eventloop.server.ServerSender;

public class KeyboardEventSender implements KeyListener {

	private final ServerSender sender;

	private boolean isEnabled;

	public KeyboardEventSender(final ServerSender sender) {
		this.sender = sender;
		this.isEnabled = true;
	}

	public void setEnabled(boolean isEnabled) {
		System.out.println("keyboard " + isEnabled);

		this.isEnabled = isEnabled;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (isEnabled) {
			sender.sendKeyPress(e.getKeyCode());
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (isEnabled) {
			sender.sendKeyRelease(e.getKeyCode());
			e.consume();
		}
	}

}
