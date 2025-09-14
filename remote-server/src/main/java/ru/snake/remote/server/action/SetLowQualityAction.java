package ru.snake.remote.server.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ru.snake.remote.server.Icons;

@SuppressWarnings("serial")
public class SetLowQualityAction extends AbstractAction {

	private final Runnable qualitySetter;

	public SetLowQualityAction(final Runnable qualitySetter) {
		this.qualitySetter = qualitySetter;

		putValue(SHORT_DESCRIPTION, "Change quality to LOW");
		putValue(LONG_DESCRIPTION, "Request client to change image quality to LOW if this level is suppoted.");
		putValue(SMALL_ICON, Icons.getQualityLowIcon(false));
		putValue(LARGE_ICON_KEY, Icons.getQualityLowIcon(true));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		qualitySetter.run();
	}

}
