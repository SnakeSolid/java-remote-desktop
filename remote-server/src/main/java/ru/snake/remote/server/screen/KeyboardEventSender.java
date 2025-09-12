package ru.snake.remote.server.screen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ru.snake.remote.eventloop.server.ServerSender;

public class KeyboardEventSender implements KeyListener {

	private final ServerSender sender;

	public KeyboardEventSender(final ServerSender sender) {
		this.sender = sender;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		sender.sendKeyPress(e.getKeyCode());
		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		sender.sendKeyRelease(e.getKeyCode());
		e.consume();
	}

}
