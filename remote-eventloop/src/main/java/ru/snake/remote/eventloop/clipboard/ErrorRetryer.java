package ru.snake.remote.eventloop.clipboard;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;

public class ErrorRetryer implements Runnable {

	private final RetryerCallback callback;

	private final AtomicInteger nAttempts;

	private ErrorRetryer(final RetryerCallback callback, final int nAttempts) {
		this.callback = callback;
		this.nAttempts = new AtomicInteger(nAttempts);
	}

	@Override
	public void run() {
		Thread thread = Thread.currentThread();

		while (nAttempts.get() > 0 && !thread.isInterrupted()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}

			try {
				SwingUtilities.invokeAndWait(this::execute);
			} catch (InvocationTargetException | InterruptedException e) {
				nAttempts.decrementAndGet();
			}
		}
	}

	private void execute() {
		try {
			callback.call();
			nAttempts.set(0);
		} catch (Exception e) {
			nAttempts.decrementAndGet();
		}
	}

	public static void start(final RetryerCallback object, final int nAttempts, String threadName) {
		ErrorRetryer retryer = new ErrorRetryer(object, nAttempts);
		Thread waiter = new Thread(retryer, threadName);
		waiter.setDaemon(true);
		waiter.start();
	}

}
