package ru.snake.remote.client;

@FunctionalInterface
public interface ClientCallback {

	void execute(String address, int port) throws Exception;

}
