package main.java.controller;


import javax.swing.*;

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
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// store the reference of the gameController (Otherwise JVM might garbage collect this object as it is not reachable)
		// Maybe shouldn't? Idk ..
		GameController gc = new GameController();
		gc.startGame();
	}
}
