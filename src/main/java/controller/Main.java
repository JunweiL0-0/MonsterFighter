package main.java.controller;

import main.java.ui.MainScreen;

/**
 * Main function.
 */
public class Main {

	/**
	 * Executed point.
	 *
	 * @param args an array of string
	 */
	public static void main(String[] args) {
		//new GameController().startGame();
		GameController gc = new GameController();
		new MainScreen(gc);
	}

}
