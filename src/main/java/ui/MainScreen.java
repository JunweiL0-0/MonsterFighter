package main.java.ui;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.controller.GameController;

public class MainScreen {
	private JFrame mainFrame;
	private JPanel bgPanel[] = new JPanel[15];
	private GameController gc;
	public MainScreen(GameController gc) {
		this.gc = gc;
		initialize();
		
	}
	public void initialize() {
	}
}
