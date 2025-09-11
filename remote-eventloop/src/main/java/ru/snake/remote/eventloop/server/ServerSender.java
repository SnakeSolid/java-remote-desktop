package ru.snake.remote.eventloop.server;

import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import ru.snake.remote.eventloop.KryoFactory;

public interface ServerSender {

	void sendClearTiles();

	public static ServerSender create(final OutputStream stream) {
		Kryo kryo = KryoFactory.kryo();
		Output output = new Output(stream);

		return new ServerSenderImpl(kryo, output);
	}

}
