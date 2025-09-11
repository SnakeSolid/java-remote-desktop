package ru.snake.remote.eventloop.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import ru.snake.remote.eventloop.message.CachedTileMessage;
import ru.snake.remote.eventloop.message.CreatedTileMessage;

public class ClientSenderImpl implements ClientSender {

	private final Kryo kryo;

	private final Output output;

	public ClientSenderImpl(final Kryo kryo, final Output output) {
		this.kryo = kryo;
		this.output = output;
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

}
