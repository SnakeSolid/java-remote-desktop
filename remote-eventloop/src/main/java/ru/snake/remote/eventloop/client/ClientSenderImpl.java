package ru.snake.remote.eventloop.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import ru.snake.remote.eventloop.message.CachedTileMessage;
import ru.snake.remote.eventloop.message.ChangeQualityMessage;
import ru.snake.remote.eventloop.message.ClipboardImageMessage;
import ru.snake.remote.eventloop.message.ClipboardTextMessage;
import ru.snake.remote.eventloop.message.CreatedTileMessage;
import ru.snake.remote.eventloop.message.ScreenSizeMessage;
import ru.snake.remote.eventloop.message.ScreenSyncMessage;

public class ClientSenderImpl implements ClientSender {

	private final Kryo kryo;

	private final Output output;

	public ClientSenderImpl(final Kryo kryo, final Output output) {
		this.kryo = kryo;
		this.output = output;
	}

	@Override
	public synchronized void sendScreenSize(int width, int height) {
		kryo.writeClassAndObject(output, new ScreenSizeMessage(width, height));
		output.flush();
	}

	@Override
	public synchronized void sendScreenSync() {
		kryo.writeClassAndObject(output, new ScreenSyncMessage());
		output.flush();
	}

	@Override
	public synchronized void sendCachedTile(int x, int y, int index) {
		kryo.writeClassAndObject(output, new CachedTileMessage(x, y, index));
		output.flush();
	}

	@Override
	public synchronized void sendCreatedTile(int x, int y, int index, byte[] data) {
		kryo.writeClassAndObject(output, new CreatedTileMessage(x, y, index, data));
		output.flush();
	}

	@Override
	public synchronized void sendCompressionQuality(int quality) {
		kryo.writeClassAndObject(output, new ChangeQualityMessage(quality));
		output.flush();
	}

	@Override
	public synchronized void sendClipboardText(String text) {
		kryo.writeClassAndObject(output, new ClipboardTextMessage(text));
		output.flush();
	}

	@Override
	public synchronized void sendClipboardImage(int width, int height, int[] data) {
		kryo.writeClassAndObject(output, new ClipboardImageMessage(width, height, data));
		output.flush();
	}

}
