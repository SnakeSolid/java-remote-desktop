package ru.snake.remote.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.KryoException;

import ru.snake.remote.eventloop.server.ServerReceiver;

public class MessageHandler implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

	private final ServerFrame frame;

	private final int clientIndex;

	private final Socket clientSocket;

	private final DefaultServer client;

	private final InputStream input;

	public MessageHandler(
		final ServerFrame frame,
		final int clientIndex,
		final Socket clientSocket,
		final DefaultServer client,
		final InputStream input
	) {
		this.frame = frame;
		this.clientIndex = clientIndex;
		this.clientSocket = clientSocket;
		this.client = client;
		this.input = input;
	}

	@Override
	public void run() {
		try {
			ServerReceiver.start(client, input);
		} finally {
			frame.removeClientTab(clientIndex);

			try {
				clientSocket.close();
			} catch (IOException | KryoException e) {
				LOG.warn("Failed to close client socket", e);
			}
		}
	}

}
