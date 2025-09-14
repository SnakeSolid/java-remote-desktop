package ru.snake.remote.server.component;

import ru.snake.remote.server.screen.ImageCanvas;

public interface ClientList {

	int addClient(String name, ImageCanvas canvas, KeyboardSwitcher keyboardSwitcher, MouseSwitcher mouseSwitcher);

	void removeClient(int index);

}
