package ru.snake.remote.server.component;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ru.snake.remote.server.Icons;
import ru.snake.remote.server.action.SwitchKeyboardAction;
import ru.snake.remote.server.action.SwitchMouseAction;

@SuppressWarnings("serial")
public class ServerFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 1024;

	private static final int DEFAULT_HEIGHT = 768;

	private ServerTabbedPane clientListPane;

	private JPanel waitingPanel;

	public ServerFrame() {
		super("Remote Desktop Server");
		setLayout(new BorderLayout());

		createComponents();
		pack();

		setIconImage(Icons.getApplicationImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setExtendedState(MAXIMIZED_BOTH);
	}

	private void createComponents() {
		clientListPane = new ServerTabbedPane();

		JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
		toolBar.setFloatable(false);
		toolBar.add(new SwitchKeyboardAction(clientListPane));
		toolBar.add(new SwitchMouseAction(clientListPane));
		toolBar.addSeparator();
		add(toolBar, BorderLayout.WEST);

		add(clientListPane, BorderLayout.CENTER);
	}

	public ClientList getClientList() {
		return clientListPane;
	}

}
