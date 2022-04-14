package main.java.controller;

import main.java.model.Generator;
import main.java.model.ItemDataStorage;
import main.java.model.Monster;
import main.java.model.Shop;
import main.java.ui.ChooseMonsterScreen;
import main.java.ui.LandingScreen;
import main.java.ui.MainScreen;
import main.java.utilities.ListGenerator;
import main.java.utilities.RandomPicker;

import java.util.ArrayList;

/**
 * A java.controller handling the game logic.
 */
public class GameController {
	private ItemDataStorage itemDataStorage;
	private ListGenerator listGenerator;
    private Generator generator;
    private Shop shop;
    private RandomPicker randomPicker;
    
    /**
     * Constructor for the GameController.
     */
    public GameController() {
        this.randomPicker = new RandomPicker();
        this.listGenerator = new ListGenerator();
        this.itemDataStorage = new ItemDataStorage(this.listGenerator);
        this.generator = new Generator(this.itemDataStorage, this.randomPicker);
        this.shop = new Shop(this.generator);
    }

    /**
     * A function used to start the game.
     */
    public void startGame() {
    	launchLandingScreen();
    }

    public void launchLandingScreen() {
        new LandingScreen(this);
    }

    public void launchChooseMonsterScreen() {
        new ChooseMonsterScreen(this);
    }

    public void launchMainScreen() {
        new MainScreen(this);
    }

    public ArrayList<Monster> getInitMonsters() {
    	return generator.getInitialMonsters();
    }
    

}
