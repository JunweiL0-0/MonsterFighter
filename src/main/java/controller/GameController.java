package main.java.controller;

import main.java.model.*;
import main.java.model.exception.TeamIsAlreadyFullException;
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
    // classes
	private ListGenerator listGenerator;
    private ItemDataStorage itemDataStorage;
    private final Generator generator;
    private Shop shop;
    private final Team team;
    private RandomPicker randomPicker;
    // variables
    private String playerName;
    private String difficulty;

    /**
     * Constructor for the GameController.
     */
    public GameController() {
        this.listGenerator = new ListGenerator();
        this.randomPicker = new RandomPicker();
        this.itemDataStorage = new ItemDataStorage(this.listGenerator);
        this.generator = new Generator(this.itemDataStorage, this.randomPicker);
        this.shop = new Shop(this.generator);
        this.team = new Team();
    }

    /**
     * A function used to start the game.
     */
    public void startGame() {
    	launchLandingScreen();
    }

    /**
     * Launch a new landingScreen
     */
    public void launchLandingScreen() {
        new LandingScreen(this);
    }

    /**
     * Launch a new chooseMonsterScreen
     */
    public void launchChooseMonsterScreen() {
        new ChooseMonsterScreen(this);
    }

    /**
     * Launch a new mainScreen
     */
    public void launchMainScreen() {
        new MainScreen(this);
    }

    /**
     * Return a list of initialMonster for the player.
     * This function is designed for the chooseMonsterScreen.
     *
     * @return a list of initialMonster for the player.
     */
    public ArrayList<Monster> getInitMonsters() {
    	return this.generator.getInitialMonsters();
    }

    /**
     * Add a new monster into the team. A TeamIsAlreadyFullException err will be thrown if the team is full.
     *
     * @param monster a monster instance
     */
    public void addMonsterToTeam(Monster monster) {
        try {
            this.team.addMonsterToTeam(monster);
        } catch (TeamIsAlreadyFullException err) {
            System.out.println("Team is full");
        }
    }

    /*
    getters go here
     */

    /**
     * Return the playerName stored in the gameController.
     *
     * @return the playerName stored in the gameController.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Return the difficulty stored in the gameController.
     *
     * @return the difficulty stored in the gameController.
     */
    public String getDifficulty() {
        return this.difficulty;
    }

    /*
    setters go here
     */

    /**
     * Store the playerName in the gameController.
     *
     * @param playerName a string represent the player's name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Store the difficulty in the gameController.
     *
     * @param difficulty a string represent the difficulty level. (easy, hard)
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
