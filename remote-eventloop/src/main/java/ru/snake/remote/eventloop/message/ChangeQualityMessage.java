package ru.snake.remote.eventloop.message;

public class ChangeQualityMessage {

	private int quality;

	public ChangeQualityMessage() {
		this.quality = 0;
	}

	public ChangeQualityMessage(final int quality) {
		this.quality = quality;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	@Override
	public String toString() {
		return "ChangeQualityMessage [quality=" + quality + "]";
	}

}
