package ru.snake.remote.core.block;

import java.awt.Color;

@FunctionalInterface
public interface DecompressorCallback {

	void setPixel(int x, int y, Color color);

}
