package main.java.ui;

<<<<<<< HEAD
import javax.swing.JPanel;

public class MainScreen {
	public JPanel bgPanel[] = new JPanel[15];
=======
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
>>>>>>> 647707f7323d117b2f2de15e2fe3ba6b8805e07d
}
