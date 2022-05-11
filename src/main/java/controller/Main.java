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
		new GameController().startGame();
	}
}
