package ru.snake.remote.client;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.KryoException;

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

	public static void execute(final String address, final int port, final boolean reconnect, final int reconnectDelay)
			throws IOException {
		while (true) {
			try (Socket socket = new Socket(address, port)) {
				socket.setTcpNoDelay(true);

				try (InputStream input = socket.getInputStream(); OutputStream output = socket.getOutputStream()) {
					RobotWrapper robot = RobotWrapper.create();
					ClientSender sender = ClientSender.create(output);
					ScreenLoop screenLoop = new ScreenLoop(sender, robot);
					Thread screenThread = new Thread(screenLoop, "Screen loop");
					screenThread.setDaemon(true);
					screenThread.start();

					DefaultClient client = new DefaultClient(sender, screenLoop, robot);
					ClientReceiver.start(client, input);
				} catch (IOException | KryoException e) {
					LOG.error("Failed to communication with server.", e);
				}
			} catch (IOException e) {
				LOG.error("Failed to connect to server.");
			}

			if (reconnect) {
				break;
			} else {
				try {
					LOG.info("Reconnecting in {} seconds...", reconnectDelay);

					Thread.sleep(Duration.ofSeconds(reconnectDelay).toMillis());
				} catch (InterruptedException ie) {
					break;
				}
			}
		}
	}

}
