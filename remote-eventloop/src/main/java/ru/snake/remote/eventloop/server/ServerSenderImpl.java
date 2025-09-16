package ru.snake.remote.eventloop.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import ru.snake.remote.eventloop.message.ChangeQualityMessage;
import ru.snake.remote.eventloop.message.ClearTilesMessage;
import ru.snake.remote.eventloop.message.KeyPressMessage;
import ru.snake.remote.eventloop.message.KeyReleaseMessage;
import ru.snake.remote.eventloop.message.MouseMoveMessage;
import ru.snake.remote.eventloop.message.MousePressMessage;
import ru.snake.remote.eventloop.message.MouseReleaseMessage;
import ru.snake.remote.eventloop.message.MouseScrollMessage;

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

	@Override
	public synchronized void sendMousePress(int x, int y, int button) {
		kryo.writeClassAndObject(output, new MousePressMessage(x, y, button));
		output.flush();
	}

	@Override
	public synchronized void sendMouseRelease(int x, int y, int button) {
		kryo.writeClassAndObject(output, new MouseReleaseMessage(x, y, button));
		output.flush();
	}

	@Override
	public synchronized void sendMouseMove(int x, int y) {
		kryo.writeClassAndObject(output, new MouseMoveMessage(x, y));
		output.flush();
	}

	@Override
	public synchronized void sendMouseScroll(int units) {
		kryo.writeClassAndObject(output, new MouseScrollMessage(units));
		output.flush();
	}

	@Override
	public synchronized void sendKeyPress(int keycode) {
		kryo.writeClassAndObject(output, new KeyPressMessage(keycode));
		output.flush();
	}

	@Override
	public synchronized void sendKeyRelease(int keycode) {
		kryo.writeClassAndObject(output, new KeyReleaseMessage(keycode));
		output.flush();
	}

	@Override
	public synchronized void sendChangeQuality(int quality) {
		kryo.writeClassAndObject(output, new ChangeQualityMessage(quality));
		output.flush();
	}

}
