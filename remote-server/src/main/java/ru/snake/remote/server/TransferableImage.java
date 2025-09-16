package ru.snake.remote.server;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TransferableImage implements Transferable {

	private static final DataFlavor[] DATA_FLAVORS = new DataFlavor[] { DataFlavor.imageFlavor };

	private final BufferedImage image;

	public TransferableImage(final BufferedImage image) {
		this.image = image;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return DATA_FLAVORS;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(DataFlavor.imageFlavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (DataFlavor.imageFlavor.equals(flavor)) {
			return image;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

}
