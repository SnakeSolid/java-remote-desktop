package ru.snake.remote.eventloop;

import com.esotericsoftware.kryo.Kryo;

import ru.snake.remote.eventloop.message.CachedTileMessage;
import ru.snake.remote.eventloop.message.ClearTilesMessage;
import ru.snake.remote.eventloop.message.CreatedTileMessage;

public class KryoFactory {

	private static final Kryo KRYO;

	static {
		KRYO = new Kryo();
		KRYO.register(byte[].class);
		KRYO.register(ClearTilesMessage.class);
		KRYO.register(CachedTileMessage.class);
		KRYO.register(CreatedTileMessage.class);
	}

	private KryoFactory() {
	}

	public synchronized static Kryo kryo() {
		return KRYO;
	}

}
