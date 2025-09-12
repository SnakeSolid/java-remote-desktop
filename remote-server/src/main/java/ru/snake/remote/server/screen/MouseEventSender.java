package ru.snake.remote.server.screen;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import ru.snake.remote.eventloop.server.ServerSender;

public class MouseEventSender implements MouseListener, MouseMotionListener, MouseWheelListener {

	private final ServerSender sender;

	private final ImageCanvas canvas;

	public MouseEventSender(final ServerSender sender, final ImageCanvas canvas) {
		this.sender = sender;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("click " + e.getClickCount());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("press " + e.getButton());

		Point point = canvas.localToImage(e.getX(), e.getY());
		sender.sendMousePress(point.x, point.y, buttonToIndex(e.getButton()));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("release " + e.getButton());

		Point point = canvas.localToImage(e.getX(), e.getY());
		sender.sendMouseRelease(point.x, point.y, buttonToIndex(e.getButton()));
	}

	private int buttonToIndex(int button) {
		switch (button) {
		case MouseEvent.BUTTON1:
			return 1;

		case MouseEvent.BUTTON2:
			return 2;

		case MouseEvent.BUTTON3:
			return 3;

		default:
			return 0;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point point = canvas.localToImage(e.getX(), e.getY());
		sender.sendMouseMove(point.x, point.y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point point = canvas.localToImage(e.getX(), e.getY());
		sender.sendMouseMove(point.x, point.y);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		sender.sendMouseScroll(e.getUnitsToScroll());
	}

}
