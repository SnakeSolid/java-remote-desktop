package ru.snake.remote.client;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import ru.snake.remote.client.screen.ScreenLoop;
import ru.snake.remote.eventloop.client.ClientReceiver;
import ru.snake.remote.eventloop.client.ClientSender;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(ScreenLoop.class);

	public static void main(String[] args) throws AWTException, HeadlessException, IOException {
		int exitCode = new CommandLine(new ClientCommand(Main::execute)).execute(args);

		System.exit(exitCode);
	}

	public static void execute(final String address, final int port)
			throws AWTException, UnknownHostException, IOException {
		try (Socket socket = new Socket(address, port)) {
			socket.setTcpNoDelay(true);

			try (InputStream input = socket.getInputStream(); OutputStream output = socket.getOutputStream()) {
				ClientSender sender = ClientSender.create(output);
				ScreenLoop screenLoop = new ScreenLoop(sender);
				Thread screenThread = new Thread(screenLoop, "Screen loop");
				screenThread.setDaemon(true);
				screenThread.start();

				DefaultClient client = new DefaultClient(sender, screenLoop);
				ClientReceiver.start(client, input);
			} catch (Exception e) {
				LOG.error("Failed to start client.", e);
			}
		} catch (Exception e) {
			LOG.error("Failed to connect to server.", e);
		}
	}

}
