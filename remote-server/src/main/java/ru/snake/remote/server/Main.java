package ru.snake.remote.server;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import ru.snake.remote.core.tile.Tile;
import ru.snake.remote.eventloop.server.ServerReceiver;
import ru.snake.remote.eventloop.server.ServerSender;
import ru.snake.remote.server.screen.ImageCanvas;
import ru.snake.remote.server.screen.KeyboardEventSender;
import ru.snake.remote.server.screen.MouseEventSender;
import ru.snake.remote.server.screen.ScreenLoop;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws AWTException, HeadlessException, IOException {
		int exitCode = new CommandLine(new ServerCommand(Main::execute)).execute(args);

		System.exit(exitCode);
	}

	public static void execute(final String address, final int port)
			throws AWTException, UnknownHostException, IOException {
		try (ServerSocket serverSocket = new ServerSocket(port, 1);) {
			LOG.info("Server is running and waiting for client connection...");
			Socket clientSocket = serverSocket.accept();
			clientSocket.setTcpNoDelay(true);

			try (InputStream input = clientSocket.getInputStream();
					OutputStream output = clientSocket.getOutputStream()) {
				LOG.info("Client connected!");

				ServerSender sender = ServerSender.create(output);

				// ------------------------------------
				ImageCanvas canvas = new ImageCanvas();
				MouseEventSender mouseSender = new MouseEventSender(sender, canvas);
				KeyboardEventSender keyboardSender = new KeyboardEventSender(sender);
				canvas.addMouseListener(mouseSender);
				canvas.addMouseMotionListener(mouseSender);
				canvas.addMouseWheelListener(mouseSender);
				canvas.addKeyListener(keyboardSender);

				JFrame frame = new JFrame("Client: " + clientSocket.getRemoteSocketAddress());
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				frame.add(canvas);
				frame.pack();
				frame.setVisible(true);
				// ------------------------------------

				BlockingQueue<Tile> decompressQueue = new ArrayBlockingQueue<>(1024);
				Thread screenLoop = new Thread(new ScreenLoop(canvas, decompressQueue), "Screen loop");
				screenLoop.setDaemon(true);
				screenLoop.start();

				DefaultServer client = new DefaultServer(null, canvas, decompressQueue);
				ServerReceiver.start(client, input);
			} catch (Exception e) {
				LOG.error("Failed start client.", e);
			}
		} catch (Exception e) {
			LOG.error("Failed to connect to server.", e);
		}
	}

}
