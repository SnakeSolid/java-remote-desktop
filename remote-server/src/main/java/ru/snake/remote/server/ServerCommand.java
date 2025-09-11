package ru.snake.remote.server;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "remote-client", mixinStandardHelpOptions = true, description = "Start remote desktop client.")
class ServerCommand implements Callable<Integer> {

	@Option(names = { "-l", "--address" }, description = "Listen on address", defaultValue = "127.0.0.1")
	private String address;

	@Option(names = { "-p", "--port" }, description = "Listen on port", defaultValue = "12398")
	private int port;

	private final ServerCallback callback;

	public ServerCommand(final ServerCallback callback) {
		this.callback = callback;
	}

	@Override
	public Integer call() throws Exception {
		callback.execute(address, port);

		return 0;
	}

	@Override
	public String toString() {
		return "ServerCommand [address=" + address + ", port=" + port + ", callback=" + callback + "]";
	}

}
