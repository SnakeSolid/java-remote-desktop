package ru.snake.remote.server;

import ru.snake.remote.core.block.CompressionQuality;
import ru.snake.remote.eventloop.server.ServerSender;
import ru.snake.remote.server.component.QualitySwitcher;

public class QualitySender implements QualitySwitcher {

	private final ServerSender sender;

	public QualitySender(final ServerSender sender) {
		this.sender = sender;
	}

	@Override
	public void setLowQuality() {
		sender.sendChangeQuality(CompressionQuality.LOW.ordinal());
	}

	@Override
	public void setMediumQuality() {
		sender.sendChangeQuality(CompressionQuality.MEDIUM.ordinal());
	}

	@Override
	public void setHighQuality() {
		sender.sendChangeQuality(CompressionQuality.HIGH.ordinal());
	}

}
