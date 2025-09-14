package ru.snake.remote.server;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import ru.snake.remote.server.component.ServerFrame;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws AWTException, HeadlessException, IOException {
		int exitCode = new CommandLine(new ServerCommand(Main::execute)).execute(args);

		if (exitCode != 0) {
			// Required to keep Swing dispatch thread running.
			System.exit(exitCode);
		}
	}

	public static void execute(final String address, final int port) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port, 1);

		LOG.info("Server is running and waiting on {}:{}...", address, port);

		ServerFrame frame = new ServerFrame();
		SwingUtilities.invokeLater(() -> frame.setVisible(true));

		Runnable acceptor = new ConnectionAcceptor(serverSocket, frame.getClientList());
		Thread server = new Thread(acceptor, "Incomint connection acceptor");
		server.setDaemon(true);
		server.start();
	}

}
