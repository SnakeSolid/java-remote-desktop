package ru.snake.remote.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.remote.core.tile.Tile;
import ru.snake.remote.eventloop.server.ServerSender;
import ru.snake.remote.server.screen.ImageCanvas;
import ru.snake.remote.server.screen.KeyboardEventSender;
import ru.snake.remote.server.screen.MouseEventSender;
import ru.snake.remote.server.screen.ScreenLoop;

public class ConnectionAcceptor implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ConnectionAcceptor.class);

	private final ServerSocket serverSocket;

	private final ServerFrame frame;

	public ConnectionAcceptor(final ServerSocket serverSocket, final ServerFrame frame) {
		this.serverSocket = serverSocket;
		this.frame = frame;
	}

	@Override
	public void run() {
		Thread currentThread = Thread.currentThread();

		while (!currentThread.isInterrupted()) {
			try {
				acceptConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void acceptConnection() throws IOException, SocketException {
		Socket clientSocket = serverSocket.accept();
		String remoteAddress = String.valueOf(clientSocket.getRemoteSocketAddress());
		clientSocket.setTcpNoDelay(true);

		InputStream input = clientSocket.getInputStream();
		OutputStream output = clientSocket.getOutputStream();

		LOG.info("Client connected from {}.", remoteAddress);

		ServerSender sender = ServerSender.create(output);

		// Create screen rendering canvas.
		ImageCanvas canvas = new ImageCanvas();
		MouseEventSender mouseSender = new MouseEventSender(sender, canvas);
		KeyboardEventSender keyboardSender = new KeyboardEventSender(sender);
		canvas.addMouseListener(mouseSender);
		canvas.addMouseMotionListener(mouseSender);
		canvas.addMouseWheelListener(mouseSender);
		canvas.addKeyListener(keyboardSender);
		int clientIndex = frame.addClientTab(remoteAddress, canvas);
		// ------------------------------------

		BlockingQueue<Tile> decompressQueue = new ArrayBlockingQueue<>(1024);
		Thread screenLoop = new Thread(
			new ScreenLoop(canvas, decompressQueue),
			String.format("Screen loop (%s)", remoteAddress)
		);
		screenLoop.setDaemon(true);
		screenLoop.start();

		DefaultServer client = new DefaultServer(null, canvas, decompressQueue);
		MessageHandler messageHandler = new MessageHandler(frame, clientIndex, clientSocket, client, input);
		Thread serverReceiver = new Thread(messageHandler, String.format("Message handler (%s)", remoteAddress));
		serverReceiver.setDaemon(true);
		serverReceiver.start();
	}

}
