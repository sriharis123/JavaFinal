package graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CommandLog extends JPanel {
	private static final long serialVersionUID = -8782011634375723048L;
	JTextArea log;
	JScrollPane scroll;

	public CommandLog() {
		log = new JTextArea();
		scroll = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setLayout(new BorderLayout());
	}
	
	public void init() {
		scroll.setBounds(super.getBounds());
		add(scroll);
		setVisible(true);
		publish("Welcome to the game.");
	}

	public void logRefresh() {
		log.append("\nRefreshing...");
		repaint();
	}
	
	public void publish(String toLog) {
		log.append("\n" + toLog);
	}
}
