package ru.snake.remote.eventloop.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import ru.snake.remote.eventloop.message.ClearTilesMessage;

public class ServerSenderImpl implements ServerSender {

	private final Kryo kryo;

	private final Output output;

	public ServerSenderImpl(final Kryo kryo, final Output output) {
		this.kryo = kryo;
		this.output = output;
	}

	@Override
	public synchronized void sendClearTiles() {
		kryo.writeClassAndObject(output, new ClearTilesMessage());
		output.flush();
	}

}
