package ru.snake.remote.server.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ru.snake.remote.server.Icons;

@SuppressWarnings("serial")
public class SetHighQualityAction extends AbstractAction {

	private final Runnable qualitySetter;

	public SetHighQualityAction(final Runnable qualitySetter) {
		this.qualitySetter = qualitySetter;

		putValue(SHORT_DESCRIPTION, "Change quality to HIGH");
		putValue(LONG_DESCRIPTION, "Request client to change image quality to HIGH if this level is suppoted.");
		putValue(SMALL_ICON, Icons.getQualityHighIcon(false));
		putValue(LARGE_ICON_KEY, Icons.getQualityHighIcon(true));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		qualitySetter.run();
	}

}
