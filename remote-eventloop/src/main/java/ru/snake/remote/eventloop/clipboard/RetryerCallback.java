package ru.snake.remote.eventloop.clipboard;

@FunctionalInterface
public interface RetryerCallback {

	void call() throws Exception;

}
