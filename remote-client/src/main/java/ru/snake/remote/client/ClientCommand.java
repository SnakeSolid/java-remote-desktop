package ru.snake.remote.client;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "remote-client", mixinStandardHelpOptions = true, description = "Start remote desktop client.")
class ClientCommand implements Callable<Integer> {

	@Option(names = { "-a", "--address" }, description = "Sever host name or address", defaultValue = "127.0.0.1")
	private String host;

	@Option(names = { "-p", "--port" }, description = "Server port number", defaultValue = "12398")
	private int port;

	private final ClientCallback callback;

	public ClientCommand(final ClientCallback callback) {
		this.callback = callback;
	}

	@Override
	public Integer call() throws Exception {
		callback.execute(host, port);

		return 0;
	}

	@Override
	public String toString() {
		return "ClientCommand [host=" + host + ", port=" + port + ", callback=" + callback + "]";
	}

}
