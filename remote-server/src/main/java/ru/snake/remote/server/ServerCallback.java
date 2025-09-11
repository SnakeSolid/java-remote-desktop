package ru.snake.remote.server;

@FunctionalInterface
public interface ServerCallback {

	void execute(String address, int port) throws Exception;

}
