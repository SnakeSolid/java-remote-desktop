package ru.snake.remote.eventloop.server;

import java.io.InputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import ru.snake.remote.eventloop.KryoFactory;
import ru.snake.remote.eventloop.message.CachedTileMessage;
import ru.snake.remote.eventloop.message.ClearTilesMessage;
import ru.snake.remote.eventloop.message.CreatedTileMessage;

public interface ServerReceiver {

	void onClearTiles();

	void onCreateTile(int x, int y, int index, byte[] data);

	void onCachedTile(int x, int y, int index);

	public static void start(final ServerReceiver receiver, final InputStream stream) {
		Kryo kryo = KryoFactory.kryo();
		Input input = new Input(stream);

		while (true) {
			Object message = kryo.readClassAndObject(input);

			if (message instanceof ClearTilesMessage) {
				receiver.onClearTiles();
			} else if (message instanceof CreatedTileMessage) {
				CreatedTileMessage m = (CreatedTileMessage) message;
				receiver.onCreateTile(m.getX(), m.getY(), m.getIndex(), m.getData());
			} else if (message instanceof CachedTileMessage) {
				CachedTileMessage m = (CachedTileMessage) message;
				receiver.onCachedTile(m.getX(), m.getY(), m.getIndex());
			} else {
				throw new RuntimeException("Unsupported message type: " + message.getClass());
			}
		}
	}

}
