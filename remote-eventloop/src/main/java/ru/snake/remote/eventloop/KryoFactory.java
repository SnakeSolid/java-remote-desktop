package ru.snake.remote.eventloop;

import com.esotericsoftware.kryo.Kryo;

import ru.snake.remote.eventloop.message.CachedTileMessage;
import ru.snake.remote.eventloop.message.ClearTilesMessage;
import ru.snake.remote.eventloop.message.CreatedTileMessage;
import ru.snake.remote.eventloop.message.MouseMoveMessage;
import ru.snake.remote.eventloop.message.MousePressMessage;
import ru.snake.remote.eventloop.message.MouseReleaseMessage;
import ru.snake.remote.eventloop.message.MouseScrollMessage;
import ru.snake.remote.eventloop.message.ScreenSizeMessage;

public class KryoFactory {

	private static final Kryo KRYO;

	static {
		KRYO = new Kryo();
		KRYO.register(byte[].class);
		KRYO.register(ClearTilesMessage.class);
		KRYO.register(ScreenSizeMessage.class);
		KRYO.register(CachedTileMessage.class);
		KRYO.register(CreatedTileMessage.class);

		KRYO.register(MousePressMessage.class);
		KRYO.register(MouseReleaseMessage.class);
		KRYO.register(MouseMoveMessage.class);
		KRYO.register(MouseScrollMessage.class);
	}

	private KryoFactory() {
	}

	public synchronized static Kryo kryo() {
		return KRYO;
	}

}
