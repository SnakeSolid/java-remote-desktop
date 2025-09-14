package ru.snake.remote.server.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ru.snake.remote.server.Icons;
import ru.snake.remote.server.component.MouseSwitcher;

@SuppressWarnings("serial")
public class SwitchMouseAction extends AbstractAction {

	private final MouseSwitcher mouseSwitcher;

	private boolean mouseEnabled;

	public SwitchMouseAction(final MouseSwitcher mouseSwitcher) {
		this.mouseSwitcher = mouseSwitcher;
		mouseEnabled = false;

		putValue(SHORT_DESCRIPTION, "Enable / disable mouse events");
		putValue(
			LONG_DESCRIPTION,
			"Enable / disable sending mouse events to client. It will enable and disable all mouse input from server."
		);
		putValue(SMALL_ICON, Icons.getMouseEnableIcon(false));
		putValue(LARGE_ICON_KEY, Icons.getMouseEnableIcon(true));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mouseSwitcher.enableMouseEvents(mouseEnabled);

		if (mouseEnabled) {
			putValue(SMALL_ICON, Icons.getMouseEnableIcon(false));
			putValue(LARGE_ICON_KEY, Icons.getMouseEnableIcon(true));

			mouseEnabled = false;
		} else {
			putValue(SMALL_ICON, Icons.getMouseDisableIcon(false));
			putValue(LARGE_ICON_KEY, Icons.getMouseDisableIcon(true));

			mouseEnabled = true;
		}
	}

}
