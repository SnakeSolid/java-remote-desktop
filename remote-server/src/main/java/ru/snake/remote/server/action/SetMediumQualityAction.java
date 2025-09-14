package ru.snake.remote.server.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ru.snake.remote.server.Icons;

@SuppressWarnings("serial")
public class SetMediumQualityAction extends AbstractAction {

	private final Runnable qualitySetter;

	public SetMediumQualityAction(final Runnable qualitySetter) {
		this.qualitySetter = qualitySetter;

		putValue(SHORT_DESCRIPTION, "Change quality to MEDIUM");
		putValue(LONG_DESCRIPTION, "Request client to change image quality to MEDIUM if this level is suppoted.");
		putValue(SMALL_ICON, Icons.getQualityMediumIcon(false));
		putValue(LARGE_ICON_KEY, Icons.getQualityMediumIcon(true));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		qualitySetter.run();
	}

}
