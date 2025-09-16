package ru.snake.remote.eventloop.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.remote.eventloop.client.ClientSender;

public class ClipboardSender implements FlavorListener {

	private static final Logger LOG = LoggerFactory.getLogger(ClipboardSender.class);

	private static final DataFlavor textFlavor = DataFlavor.stringFlavor;

	private static final DataFlavor imageFlavor = DataFlavor.imageFlavor;

	private static final int MAX_ATTEMPTS = 5;

	private static final int MAX_STRING_LENGTH = 1024 * 1024;

	private static final int MAX_IMAGE_SIZE = 2048 * 2048;

	private final Clipboard clipboard;

	private final ClientSender sender;

	private boolean enabled;

	private ClipboardSender(final Clipboard clipboard, final ClientSender sender) {
		this.clipboard = clipboard;
		this.sender = sender;
		this.enabled = true;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void flavorsChanged(FlavorEvent event) {
		if (!enabled) {
			return;
		}

		try {
			sendBufferContent();
		} catch (IllegalStateException e) {
			// System clipboard may be temporarily locked by other applications
			// (particularly in Windows environments and RDP sessions), causing
			// IllegalStateException. Retry attempts with delay help overcome
			// this issue by waiting for the clipboard to become available.
			ErrorRetryer.start(this::sendBufferContent, MAX_ATTEMPTS, "Clipboard waiter");
		} catch (UnsupportedFlavorException | IOException e) {
			LOG.warn("Failed to send clipboard content", e);
		}
	}

	private void sendBufferContent() throws UnsupportedFlavorException, IOException {
		Transferable transferable = clipboard.getContents(null);

		if (transferable.isDataFlavorSupported(textFlavor)) {
			String text = (String) transferable.getTransferData(textFlavor);

			if (text.length() <= MAX_STRING_LENGTH) {
				sender.sendClipboardText(text);
			}
		} else if (transferable.isDataFlavorSupported(imageFlavor)) {
			BufferedImage image = (BufferedImage) transferable.getTransferData(imageFlavor);
			int width = image.getWidth();
			int height = image.getHeight();

			if (width > 0 && height > 0 && width * height <= MAX_IMAGE_SIZE) {
				int[] data = new int[width * height];
				image.getRGB(0, 0, width, height, data, 0, width);

				sender.sendClipboardImage(width, height, data);
			}
		}
	}

	public void register() {
		clipboard.addFlavorListener(this);
	}

	public void deregister() {
		clipboard.removeFlavorListener(this);
	}

	@Override
	public String toString() {
		return "ClipboardSender [clipboard=" + clipboard + ", sender=" + sender + "]";
	}

	public static ClipboardSender create(final ClientSender sender) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		return new ClipboardSender(clipboard, sender);
	}

}
