package ru.snake.remote.client;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "remote-client", mixinStandardHelpOptions = true, description = "Start remote desktop client.")
class ClientCommand implements Callable<Integer> {

	@Option(names = { "-a", "--address" }, description = "Server hostname or IP address", defaultValue = "127.0.0.1")
	private String address;

	@Option(names = { "-p", "--port" }, description = "Server port number to connect to", defaultValue = "12398")
	private int port;

	@Option(names = { "-r", "--reconnect" }, description = "Reconnect until the server becomes available")
	private boolean reconnect;

	@Option(
		names = { "-d", "--reconnect-delay" },
		description = "Delay between connection attempts in seconds",
		defaultValue = "5"
	)
	private int reconnectDelay;

	private final ClientCallback callback;

	public ClientCommand(final ClientCallback callback) {
		this.callback = callback;
	}

	@Override
	public Integer call() throws Exception {
		callback.execute(address, port, reconnect, reconnectDelay);

		return 0;
	}

	@Override
	public String toString() {
		return "ClientCommand [address=" + address + ", port=" + port + ", reconnect=" + reconnect + ", reconnectDelay="
				+ reconnectDelay + ", callback=" + callback + "]";
	}

}
