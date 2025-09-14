package ru.snake.remote.core.block;

import java.awt.Color;

@FunctionalInterface
public interface BlockDecompressorCallback {

	void setPixel(int x, int y, Color color);

}
