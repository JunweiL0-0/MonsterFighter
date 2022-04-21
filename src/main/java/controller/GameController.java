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
    private Generator generator;
    private Shop shop;
    private Team team;
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
    	return this.generator.getInitialMonsters();
    }

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
    public String getPlayerName() {
        return this.playerName;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    /*
    setters go here
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
