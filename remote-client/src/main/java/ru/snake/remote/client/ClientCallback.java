package ru.snake.remote.client;

@FunctionalInterface
public interface ClientCallback {

	void execute(String address, int port, boolean reconnect, int reconnectDelay) throws Exception;

}
