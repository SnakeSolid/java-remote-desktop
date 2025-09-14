package ru.snake.remote.core.block;

import ru.snake.remote.core.image.HSLBuffer;

public interface BlockCompressor {

	int getWidth();

	int getHeight();

	byte[] compress(int x, int y, HSLBuffer buffer);

}
