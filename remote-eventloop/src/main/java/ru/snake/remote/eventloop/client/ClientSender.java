package ru.snake.remote.eventloop.client;

import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import ru.snake.remote.eventloop.KryoFactory;

public interface ClientSender {

	void sendScreenSize(int width, int height);

	void sendCachedTile(int x, int y, int index);

	void sendCreatedTile(int x, int y, int index, byte[] data);

	void sendCompressionQuality(int quality);

	public static ClientSender create(final OutputStream stream) {
		Kryo kryo = KryoFactory.kryo();
		Output output = new Output(stream);

		return new ClientSenderImpl(kryo, output);
	}

}
