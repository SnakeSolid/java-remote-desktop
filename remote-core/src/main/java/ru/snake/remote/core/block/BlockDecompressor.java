package ru.snake.remote.core.block;

public interface BlockDecompressor {

	int getWidth();

	int getHeight();

	int getLength();

	void decompress(final byte[] buffer, final BlockDecompressorCallback callback);

}
