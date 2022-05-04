package main.java.controller;

import main.java.model.*;
import main.java.model.exception.TeamIsAlreadyFullException;
import main.java.ui.ChooseMonsterScreen;
import main.java.ui.LandingScreen;
import main.java.ui.MainScreen;
import main.java.utilities.ListGenerator;
import main.java.utilities.RandomPicker;

import java.util.ArrayList;
import java.util.Objects;

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
    private int currentDay;
    private int totalDay;
    private int gold;
    private int point;

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
        initCurrAndTotalDay();
        initGold();
        initPoint();
        new MainScreen(this);
    }

    /**
     * Return a list of initialMonster for the player.
     * This function is designed for the chooseMonsterScreen.
     *
     * @return a list of initialMonster for the player.
     */
    public ArrayList<Monster> getInitMonsters() {
    	return this.generator.generateInitialMonsters();
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

    public void incrementDay() {
        if (this.currentDay <= this.totalDay) {
            this.currentDay += 1;
        }
    }

    private void initCurrAndTotalDay() {
        this.currentDay = 1;
        if (isEasyMode()) {
            this.totalDay = 10;
        } else if (isHardMode()) {
            this.totalDay = 5;
        }
    }

    private void initGold() {
        if (isEasyMode()) {
            this.gold = 100;
        } else if (isHardMode()) {
            this.gold = 200;
        }
    }

    private void initPoint() {
        if (isEasyMode()) {
            this.point = 500;
        } else if (isHardMode()) {
            this.point = 600;
        }
    }

    /**
     * Return true if the game is in easy mode. False otherwise.
     *
     * @return true if game is in easy mode. False otherwise.
     */
    private boolean isEasyMode() {
        return Objects.equals(this.difficulty, "Easy");
    }

    /**
     * Return true if the game is in hard mode. False otherwise.
     *
     * @return true if game is in hard mode. False otherwise.
     */
    private boolean isHardMode() {
        return Objects.equals(this.difficulty, "Hard");
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

    public int getGold() {
        return this.gold;
    }

    public int getPoint() {
        return this.point;
    }

    public int getCurrentDay() {
        return this.currentDay;
    }

    public int getTotalDay() {
        return this.totalDay;
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

    /**
     * Store the gold in the gameController.
     *
     * @param gold an integer represent the current gold the player has.
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Store the point in the gameController.
     *
     * @param point a integer represent the current point the player has.
     */
    public void setPoint(int point) {
        this.point = point;
    }

}
