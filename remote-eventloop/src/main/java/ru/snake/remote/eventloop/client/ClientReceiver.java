package ru.snake.remote.eventloop.client;

import java.io.InputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import ru.snake.remote.eventloop.KryoFactory;
import ru.snake.remote.eventloop.message.ClearTilesMessage;

public interface ClientReceiver {

	void onClearTiles();

	public static void start(final ClientReceiver receiver, final InputStream stream) {
		Kryo kryo = KryoFactory.kryo();
		Input input = new Input(stream);

		while (true) {
			Object message = kryo.readClassAndObject(input);

			if (message instanceof ClearTilesMessage) {
				receiver.onClearTiles();
			} else {
				throw new RuntimeException("Unsupported message type: " + message.getClass());
			}
		}
	}

}
