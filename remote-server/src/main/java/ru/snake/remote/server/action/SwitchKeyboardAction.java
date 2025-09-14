package ru.snake.remote.server.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ru.snake.remote.server.Icons;
import ru.snake.remote.server.component.KeyboardSwitcher;

@SuppressWarnings("serial")
public class SwitchKeyboardAction extends AbstractAction {

	private final KeyboardSwitcher keyboardSwitcher;

	private boolean keyboardEnabled;

	public SwitchKeyboardAction(final KeyboardSwitcher keyboardSwitcher) {
		this.keyboardSwitcher = keyboardSwitcher;
		keyboardEnabled = false;

		putValue(SHORT_DESCRIPTION, "Enable / disable keyboard events");
		putValue(
			LONG_DESCRIPTION,
			"Enable / disable sending keyboard events to client. It will enable and disable all keyboard input from server."
		);
		putValue(SMALL_ICON, Icons.getKeyboardEnableIcon(false));
		putValue(LARGE_ICON_KEY, Icons.getKeyboardEnableIcon(true));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		keyboardSwitcher.enableKeyboardEvents(keyboardEnabled);

		if (keyboardEnabled) {
			putValue(SMALL_ICON, Icons.getKeyboardEnableIcon(false));
			putValue(LARGE_ICON_KEY, Icons.getKeyboardEnableIcon(true));

			keyboardEnabled = false;
		} else {
			putValue(SMALL_ICON, Icons.getKeyboardDisableIcon(false));
			putValue(LARGE_ICON_KEY, Icons.getKeyboardDisableIcon(true));

			keyboardEnabled = true;
		}
	}

}
