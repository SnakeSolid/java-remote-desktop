package ru.snake.remote.server.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ru.snake.remote.server.screen.ImageCanvas;

@SuppressWarnings("serial")
public class ServerTabbedPane extends JTabbedPane
		implements ClientList, KeyboardSwitcher, MouseSwitcher, QualitySwitcher {

	private final JPanel waitingPanel;

	private final Map<Integer, KeyboardSwitcher> keyboard;

	private final Map<Integer, MouseSwitcher> mouse;

	private final Map<Integer, QualitySwitcher> quality;

	public ServerTabbedPane() {
		super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		this.waitingPanel = createWaitingPanel();
		this.keyboard = new HashMap<>();
		this.mouse = new HashMap<>();
		this.quality = new HashMap<>();
	}

	private JPanel createWaitingPanel() {
		JPanel waitingPanel = new JPanel(new BorderLayout());
		JLabel waitingLabel = new JLabel("Waiting for client connections...", JLabel.CENTER);
		waitingLabel.setFont(new Font("Arial", Font.BOLD, 16));

		return waitingPanel;
	}

	@Override
	public int addClient(
		final String name,
		final ImageCanvas canvas,
		final KeyboardSwitcher keyboardSwitcher,
		final MouseSwitcher mouseSwitcher,
		final QualitySwitcher qualitySwitcher
	) {
		int tabCount = getTabCount();

		for (int index = tabCount - 1; index >= 0; index -= 1) {
			Component component = getComponentAt(index);

			if (component == waitingPanel) {
				remove(index);
			}
		}

		int tabIndex = getTabCount();
		addTab(name, canvas);
		setSelectedIndex(tabIndex);
		canvas.grabFocus();

		keyboard.put(tabIndex, keyboardSwitcher);
		mouse.put(tabIndex, mouseSwitcher);
		quality.put(tabIndex, qualitySwitcher);

		return tabIndex;
	}

	@Override
	public void removeClient(final int index) {
		int tabCount = getTabCount();

		if (index < tabCount) {
			keyboard.remove(index);
			mouse.remove(index);
			quality.remove(index);

			remove(index);
		}

		if (getTabCount() == 0) {
			addTab("No connections", waitingPanel);
		}
	}

	@Override
	public void enableKeyboardEvents(boolean enabled) {
		int tabIndex = getSelectedIndex();
		KeyboardSwitcher switcher = keyboard.get(tabIndex);

		if (switcher != null) {
			switcher.enableKeyboardEvents(enabled);
		}
	}

	@Override
	public void enableMouseEvents(boolean enabled) {
		int tabIndex = getSelectedIndex();
		MouseSwitcher switcher = mouse.get(tabIndex);

		if (switcher != null) {
			switcher.enableMouseEvents(enabled);
		}
	}

	@Override
	public void setLowQuality() {
		int tabIndex = getSelectedIndex();
		QualitySwitcher switcher = quality.get(tabIndex);

		if (switcher != null) {
			switcher.setLowQuality();
		}
	}

	@Override
	public void setMediumQuality() {
		int tabIndex = getSelectedIndex();
		QualitySwitcher switcher = quality.get(tabIndex);

		if (switcher != null) {
			switcher.setMediumQuality();
		}
	}

	@Override
	public void setHighQuality() {
		int tabIndex = getSelectedIndex();
		QualitySwitcher switcher = quality.get(tabIndex);

		if (switcher != null) {
			switcher.setHighQuality();
		}
	}

}
