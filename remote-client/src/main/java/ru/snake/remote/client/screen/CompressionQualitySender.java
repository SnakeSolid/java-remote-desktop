package ru.snake.remote.client.screen;

import ru.snake.remote.core.block.CompressionQuality;
import ru.snake.remote.eventloop.client.ClientSender;

public class CompressionQualitySender {

	private final ClientSender sender;

	private CompressionQuality quality;

	public CompressionQualitySender(final ClientSender sender, final CompressionQuality quality) {
		this.sender = sender;
		this.quality = quality;
	}

	public boolean send(CompressionQuality compressionQuality) {
		if (quality != compressionQuality) {
			quality = compressionQuality;

			sender.sendCompressionQuality(quality.ordinal());

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "CompressionQualitySender [sender=" + sender + ", quality=" + quality + "]";
	}

}
