package main.java.controller;

import main.java.model.*;
import main.java.utilities.Observable;
import main.java.view.ChooseMonsterScreen;
import main.java.view.LandingScreen;
import main.java.view.MainScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A java controller handling the game logic.
 */
public class GameController extends Observable {
    // classes
    private final MonsterGenerator monsterGenerator;
    private final MedicineGenerator medicineGenerator;
    private final WeaponGenerator weaponGenerator;
    private final ShieldGenerator shieldGenerator;
    private Shop shop;
    private final Team playerTeam;
    // variables
    private String playerName;
    private String difficulty;
    private int currentDay;
    private int totalDay;
    private int gold;
    private int point;
    private int battleIndex;
    private ArrayList<Team> battles;
    // used for observers
    private boolean isEncounteredBattle;
    private boolean isUpdateTeam;
    private boolean isAbleToStartFight;
    private boolean isAbleToReorderTeam;

    /**
     * Constructor for the GameController.
     */
    public GameController() {
        this.monsterGenerator = new MonsterGenerator();
        this.medicineGenerator = new MedicineGenerator();
        this.weaponGenerator = new WeaponGenerator();
        this.shieldGenerator = new ShieldGenerator();
        this.shop = new Shop(this);
        this.playerTeam = new Team(4);
        this.battles = new ArrayList<>();
        this.battleIndex = 999;
        this.isEncounteredBattle = false;
        this.isUpdateTeam = false;
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
        initBattles(this.currentDay);
        new MainScreen(this);
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

    public Team getEnemyTeam() {
        return this.battles.get(this.battleIndex);
    }

    private void initBattles(int currentDay) {
        int totalBattle = 0;
        int monsterPerTeam = 0;
        if (currentDay < 5) {
            totalBattle = 2;
            monsterPerTeam = 2;
        } else if (currentDay < 10) {
            totalBattle = 3;
            monsterPerTeam = 3;
        } else {
            totalBattle = 4;
            monsterPerTeam = 4;
        }
        for (int i=0; i<totalBattle; i++) {
            Team enemyTeam = new Team(monsterPerTeam);
            for (Monster m : this.monsterGenerator.generateMonster(monsterPerTeam)) {
                enemyTeam.addMonsterToTeam(m);
            }
            this.battles.add(enemyTeam);
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
        int prev_val = this.gold;
        this.gold = gold;
        if (prev_val != this.gold) {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Store the point in the gameController.
     *
     * @param point a integer represent the current point the player has.
     */
    public void setPoint(int point) {
        int prev_val = this.point;
        this.point = point;
        if (prev_val != this.point) {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Add a new monster into the team. A TeamIsAlreadyFullException err will be thrown if the team is full.
     *
     * @param monster a monster instance
     */
    public void addMonsterToPlayerTeam(Monster monster) {
        boolean added = this.playerTeam.addMonsterToTeam(monster);
        if (!added) {
            System.out.println("Team is full. Can not add monster into team");
        }
    }

    public ArrayList<Monster> getMonsterTeamMember() {
        return this.playerTeam.getTeamMember();
    }

    public void incrementDay() {
        if (this.currentDay <= this.totalDay) {
            this.currentDay += 1;
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Return a list of initialMonster for the player.
     * This function is designed for the chooseMonsterScreen.
     *
     * @return a list of initialMonster for the player.
     */
    public ArrayList<Monster> getInitMonsters() {
        return this.monsterGenerator.generateInitialMonsters();
    }

    public Monster getMonsterFromTeamByIndex(int i) {
        return this.playerTeam.getMonsterByIndex(i);
    }
    
    public Monster generateMonster() {
    	Monster monster = monsterGenerator.generateMonster();
    	return monster;
    }
    
    public Medicine generateMedicine() {
    	Medicine med = medicineGenerator.generateMedicine();
    	return med;
    }
    
    public Weapon generateWeapon() {
    	Weapon weapon = weaponGenerator.generateWeapon();
    	return weapon;
    }
    
    public Shield generateShield() {
    	Shield shield = shieldGenerator.generateShield();
    	return shield;
    }

    public void storeBattleIndex(int battleIndex) {
        if (this.battleIndex != battleIndex) {
            this.battleIndex = battleIndex;
            this.isEncounteredBattle = true;
            setChanged();
            notifyObservers();
        }
    }

    public boolean isAbleToStartFight() {
        return this.battleIndex != 999 && this.playerTeam.size() != 0;
    }

    public boolean isAbleToReorderTeam() {
        return this.playerTeam.size() != 0;
    }

    public boolean isEncounteredBattle() {
        boolean prevVal = this.isEncounteredBattle;
        this.isEncounteredBattle = false;
        return prevVal;
    }

    public boolean isUpdateTeam() {
        boolean prevVal = this.isUpdateTeam;
        this.isUpdateTeam = false;
        return prevVal;
    }
}
