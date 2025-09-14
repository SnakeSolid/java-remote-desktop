package ru.snake.remote.eventloop.server;

import java.io.InputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import ru.snake.remote.eventloop.KryoFactory;
import ru.snake.remote.eventloop.message.CachedTileMessage;
import ru.snake.remote.eventloop.message.ChangeQualityMessage;
import ru.snake.remote.eventloop.message.ClearTilesMessage;
import ru.snake.remote.eventloop.message.CreatedTileMessage;
import ru.snake.remote.eventloop.message.ScreenSizeMessage;
import ru.snake.remote.eventloop.message.ScreenSyncMessage;

public interface ServerReceiver {

	void onClearTiles();

	void onScreenSize(int width, int height);

	void onScreenSync();

	void onCreateTile(int x, int y, int index, byte[] data);

	void onCachedTile(int x, int y, int index);

	void onChangeQuality(int quality);

	public static void start(final ServerReceiver receiver, final InputStream stream) {
		Kryo kryo = KryoFactory.kryo();
		Input input = new Input(stream);

		while (true) {
			Object message = kryo.readClassAndObject(input);

			if (message instanceof ClearTilesMessage) {
				receiver.onClearTiles();
			} else if (message instanceof ScreenSizeMessage) {
				ScreenSizeMessage m = (ScreenSizeMessage) message;
				receiver.onScreenSize(m.getWidth(), m.getHeight());
			} else if (message instanceof ScreenSyncMessage) {
				receiver.onScreenSync();
			} else if (message instanceof CreatedTileMessage) {
				CreatedTileMessage m = (CreatedTileMessage) message;
				receiver.onCreateTile(m.getX(), m.getY(), m.getIndex(), m.getData());
			} else if (message instanceof CachedTileMessage) {
				CachedTileMessage m = (CachedTileMessage) message;
				receiver.onCachedTile(m.getX(), m.getY(), m.getIndex());
			} else if (message instanceof ChangeQualityMessage) {
				ChangeQualityMessage m = (ChangeQualityMessage) message;
				receiver.onChangeQuality(m.getQuality());
			} else {
				throw new RuntimeException("Unsupported message type: " + message.getClass());
			}
		}
	}

}
