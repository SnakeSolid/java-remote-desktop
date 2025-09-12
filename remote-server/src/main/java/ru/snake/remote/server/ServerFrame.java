package ru.snake.remote.server;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ru.snake.remote.server.screen.ImageCanvas;

@SuppressWarnings("serial")
public class ServerFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 1024;

	private static final int DEFAULT_HEIGHT = 768;

	private JTabbedPane tabbedPane;

	private JPanel waitingPanel;

	public ServerFrame() {
		super("Remote Desktop Server");

		createComponents();
		pack();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setExtendedState(MAXIMIZED_BOTH);
	}

	private void createComponents() {
		tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		waitingPanel = new JPanel(new BorderLayout());

		JLabel waitingLabel = new JLabel("Waiting for client connections...", JLabel.CENTER);
		waitingLabel.setFont(new Font("Arial", Font.BOLD, 16));
		waitingPanel.add(waitingLabel);
		tabbedPane.addTab("No connections", waitingPanel);

		add(tabbedPane);
	}

	public int addClientTab(final String name, final ImageCanvas canvas) {
		int tabCount = tabbedPane.getTabCount();

		for (int index = tabCount - 1; index >= 0; index -= 1) {
			Component component = tabbedPane.getComponentAt(index);

			if (component == waitingPanel) {
				tabbedPane.remove(index);
			}
		}

		int tabIndex = tabbedPane.getTabCount();
		tabbedPane.addTab(name, canvas);
		tabbedPane.setSelectedIndex(tabIndex);

		return tabIndex;
	}

	public void removeClientTab(final int index) {
		int tabCount = tabbedPane.getTabCount();

		if (index < tabCount) {
			tabbedPane.remove(index);
		}

		if (tabbedPane.getTabCount() == 0) {
			tabbedPane.addTab("No connections", waitingPanel);
		}
	}

}
