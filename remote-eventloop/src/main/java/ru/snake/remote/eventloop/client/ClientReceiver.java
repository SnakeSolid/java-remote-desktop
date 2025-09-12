package ru.snake.remote.eventloop.client;

import java.io.InputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import ru.snake.remote.eventloop.KryoFactory;
import ru.snake.remote.eventloop.message.ClearTilesMessage;
import ru.snake.remote.eventloop.message.MouseMoveMessage;
import ru.snake.remote.eventloop.message.MousePressMessage;
import ru.snake.remote.eventloop.message.MouseReleaseMessage;
import ru.snake.remote.eventloop.message.MouseScrollMessage;

public interface ClientReceiver {

	void onClearTiles();

	void onMousePress(int x, int y, int button);

	void onMouseRelease(int x, int y, int button);

	void onMouseMove(int x, int y);

	void onMouseScroll(int units);

	public static void start(final ClientReceiver receiver, final InputStream stream) {
		Kryo kryo = KryoFactory.kryo();
		Input input = new Input(stream);

		while (true) {
			Object message = kryo.readClassAndObject(input);

			if (message instanceof ClearTilesMessage) {
				receiver.onClearTiles();
			} else if (message instanceof MousePressMessage) {
				MousePressMessage m = (MousePressMessage) message;
				receiver.onMousePress(m.getX(), m.getY(), m.getButton());
			} else if (message instanceof MouseReleaseMessage) {
				MouseReleaseMessage m = (MouseReleaseMessage) message;
				receiver.onMouseRelease(m.getX(), m.getY(), m.getButton());
			} else if (message instanceof MouseMoveMessage) {
				MouseMoveMessage m = (MouseMoveMessage) message;
				receiver.onMouseMove(m.getX(), m.getY());
			} else if (message instanceof MouseScrollMessage) {
				MouseScrollMessage m = (MouseScrollMessage) message;
				receiver.onMouseScroll(m.getUnits());
			} else {
				throw new RuntimeException("Unsupported message type: " + message.getClass());
			}
		}
	}

}
