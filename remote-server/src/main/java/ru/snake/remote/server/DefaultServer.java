package ru.snake.remote.server;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.remote.core.block.CompressionQuality;
import ru.snake.remote.core.tile.CachedTile;
import ru.snake.remote.core.tile.CreatedTile;
import ru.snake.remote.eventloop.server.ServerReceiver;
import ru.snake.remote.server.screen.ImageCanvas;
import ru.snake.remote.server.screen.ScreenOperation;

public class DefaultServer implements ServerReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultServer.class);

	private final ImageCanvas canvas;

	private final BlockingQueue<ScreenOperation> tileQueue;

	private final Clipboard clipboard;

	public DefaultServer(final ImageCanvas canvas, final BlockingQueue<ScreenOperation> screenQueue) {
		this.canvas = canvas;
		this.tileQueue = screenQueue;
		this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	@Override
	public void onClearTiles() {
	}

	@Override
	public void onScreenSize(int width, int height) {
		try {
			SwingUtilities.invokeAndWait(() -> canvas.setImageSize(width, height));
		} catch (InvocationTargetException | InterruptedException e) {
			LOG.warn("Failed to change screen size", e);
		}
	}

	@Override
	public void onScreenSync() {
		try {
			tileQueue.put(ScreenOperation.sync());
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void onCreateTile(int x, int y, int index, byte[] data) {
		CreatedTile tile = new CreatedTile(x, y, index, data);

		try {
			tileQueue.put(new ScreenOperation(null, tile, null, false));
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void onCachedTile(int x, int y, int index) {
		CachedTile tile = new CachedTile(x, y, index);

		try {
			tileQueue.put(new ScreenOperation(null, null, tile, false));
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void onChangeQuality(int quality) {
		CompressionQuality[] values = CompressionQuality.values();

		if (quality >= 0 && quality < values.length) {
			try {
				tileQueue.put(ScreenOperation.quality(values[quality]));
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void onClipboardText(String text) {
		Transferable transferable = new StringSelection(text);
		clipboard.setContents(transferable, null);
	}

	@Override
	public void onClipboardImage(int width, int height, int[] data) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, width, height, data, 0, width);

		Transferable transferable = new TransferableImage(image);
		clipboard.setContents(transferable, null);
	}

}
