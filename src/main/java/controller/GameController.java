package main.java.controller;

import main.java.model.*;
import main.java.utilities.Observable;
import main.java.view.ChooseMonsterScreen;
import main.java.view.LandingScreen;
import main.java.view.MainScreen;
import sun.misc.SignalHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * A java controller handling the game logic.
 */
public class GameController extends Observable {
    // classes
    private final MonsterGenerator monsterGenerator;
    private final MedicineGenerator medicineGenerator;
    private final WeaponGenerator weaponGenerator;
    private final ShieldGenerator shieldGenerator;
    private final Shop shop;
    private final Team playerTeam;
    private final BattleField battleField;
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
    private boolean isUpdateEnemyTeam;
    private boolean isBattleOccur;
    private boolean isPlayerWon;
    private boolean isEnemyWon;
    private boolean isRefreshAllPressed;
    private boolean isNextDay;
    private boolean isGameOver;
    private boolean isUpdateBag;
    private boolean isUpdatePoint;
    private boolean isUpdateGold;


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
        this.battleField = new BattleField();
        this.battles = new ArrayList<>();
        this.battleIndex = -1;
        this.isEncounteredBattle = false;
        this.isUpdateTeam = false;
        this.isAbleToStartFight = false;
        this.isAbleToReorderTeam = false;
        this.isUpdateEnemyTeam = false;
        this.isBattleOccur = false;
        this.isRefreshAllPressed = false;
        this.isNextDay = false;
        this.isGameOver = false;
        this.isUpdateBag = false;
        this.isUpdatePoint = false;
        this.isUpdateGold = false;
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
        initBattles();
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
            this.gold = 500;
        } else if (isHardMode()) {
            this.gold = 1000;
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

    public int battleLeft() {
        return this.battles.size();
    }

    private void initBattles() {
        int totalBattle = 0;
        int monsterPerTeam = 0;
        if (this.currentDay < 5) {
            totalBattle = 2;
            monsterPerTeam = 2;
        } else if (this.currentDay < 10) {
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
            this.isUpdateGold = true;
            setChanged();
            notifyObservers();
        }
    }

    public boolean isUpdateGold() {
        boolean prevVal = this.isUpdateGold;
        this.isUpdateGold = false;
        return prevVal;
    }

    public boolean isUpdatePoint() {
        boolean prevVal = this.isUpdatePoint;
        this.isUpdatePoint = false;
        return prevVal;
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
            this.isUpdatePoint = true;
            setChanged();
            notifyObservers();
        }
    }

    public Shop getShop() {
        return this.shop;
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

    public void renameTeamMonsterByIndex(int monsterIndex, String newName) {
        this.playerTeam.renameMonsterByIndex(monsterIndex, newName);
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
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

    public void swapMonsterInPlayerTeam(int monsterIndex1, int monsterIndex2) {
        this.playerTeam.swapMonster(monsterIndex1, monsterIndex2);
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }

    // the player will enter battle when the Fight btn being pressed on the MainScreen
    // the validation will be done by other functions
    public void enterBattle() {
        if (!(this.playerTeam.isAllFainted()) && this.playerTeam.size() > 0 && battleIndex != -1) {
            this.battleField.setBattle(this.playerTeam, this.battles.get(this.battleIndex));
            this.isUpdateTeam = true;
            this.isUpdateEnemyTeam = true;
            this.isBattleOccur = true;
            setChanged();
            notifyObservers();
        }
    }

    public void addItemToBag(Monster m) {
        this.playerTeam.addItemToMonsterBag(m);
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    public void addItemToBag(Weapon w) {
        this.playerTeam.addItemToWeaponBag(w);
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    public void addItemToBag(Shield s) {
        this.playerTeam.addItemToShieldBag(s);
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    public void addItemToBag(Medicine m) {
        this.playerTeam.addItemToMedicineBag(m);
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    public void addBagMonsterToTeam(int bagMonsterIndex) {
        this.playerTeam.addBagMonsterToTeam(bagMonsterIndex);
        this.isUpdateTeam = true;
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    public ArrayList<Monster> getMonsterBag() {
        return this.playerTeam.getMonsterBag();
    }

    public ArrayList<Weapon> getWeaponBag() {
        return this.playerTeam.getWeaponBag();
    }
    public ArrayList<Shield> getShieldBag() {
        return this.playerTeam.getShieldBag();
    }
    public ArrayList<Medicine> getMedicineBag() {
        return this.playerTeam.getMedicineBag();
    }

    public void sellItem(Monster m, int monsterIndex) {
        this.gold += m.getRefundPrice();
        this.playerTeam.getMonsterBag().remove(monsterIndex);
        this.isUpdateBag = true;
        this.isUpdateGold = true;
        setChanged();
        notifyObservers();
    }

    public void sellItem(Medicine m, int medicineIndex) {
        this.gold += m.getRefundPrice();
        this.playerTeam.getMedicineBag().remove(medicineIndex);
        this.isUpdateBag = true;
        this.isUpdateGold = true;
        setChanged();
        notifyObservers();
    }

    public void sellItem(Weapon w, int weaponIndex) {
        this.gold += w.getRefundPrice();
        this.playerTeam.getWeaponBag().remove(weaponIndex);
        this.isUpdateBag = true;
        this.isUpdateGold = true;
        setChanged();
        notifyObservers();
    }

    public void sellItem(Shield s, int shieldIndex) {
        this.gold += s.getRefundPrice();
        this.playerTeam.getShieldBag().remove(shieldIndex);
        this.isUpdateBag = true;
        this.isUpdateGold = true;
        setChanged();
        notifyObservers();
    }

    public boolean isUpdateBag() {
        boolean prevVal = this.isUpdateBag;
        this.isUpdateBag = false;
        return prevVal;
    }

    public void existBattle() {
        this.battleField.endBattle();
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }

    public void nextDay() {
        if (this.currentDay < this.totalDay) {
            randomEvent();
            this.battles.clear();
            initBattles();
            this.battleIndex = -1;
            this.currentDay++;
            this.shop.refreshShop();
            this.playerTeam.healAllMonster();
            this.isNextDay = true;
        } else {
            this.isGameOver = true;
        }
        setChanged();
        notifyObservers();
    }

    private void randomEvent() {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            int eventId = rand.nextInt(3);
            if (eventId == 0 && this.playerTeam.size() < this.playerTeam.getMaxTeamMember()) {
                // new monster added
                this.playerTeam.addMonsterToTeam(this.generateMonster());
            } else if (eventId == 1 && this.playerTeam.size() > 0) {
                // level up
                this.playerTeam.getMonsterByIndex(rand.nextInt(this.playerTeam.size())).levelUp();
            } else if (eventId == 2 && this.playerTeam.size() > 0) {
                // monster leave
                int monsterIndex = this.playerTeam.getLeastHealthMonsterIndex();
                this.playerTeam.removeTeamMemberByIndex(monsterIndex);
            }
        }
    }

    public void battle() {
        // 1: player won, -1: enemy Won, 0: nobody won yet
        int result = this.battleField.battle();
        if (result == 1) {
            this.isPlayerWon = true;
            this.gold += 50;
            this.point += 100;
            this.battles.remove(this.battleIndex);
            this.battleIndex = -1;
        } else if (result == (-1)) {
            this.isEnemyWon = true;
        } else {
            this.isBattleOccur = true;
        }
        this.isUpdateTeam = true;
        this.isUpdateEnemyTeam = true;
        setChanged();
        notifyObservers();
    }

    public int getTotalBattle() {
        return this.battles.size();
    }

    public int getBattleIndex() {
        return this.battleIndex;
    }

    public Monster getPlayerTeamReadyMonster() {
        return this.battleField.getPlayerTeamReadyMonster();
    }

    public Monster getEnemyTeamReadyMonster() {
        return this.battleField.getEnemyTeamReadyMonster();
    }

    public boolean isPlayerTurnBattle() {
        return this.battleField.isPlayerTurn();
    }

    public boolean isAbleToStartFight() {
        return this.battleIndex != -1 && this.playerTeam.size() != 0 && !(this.playerTeam.isAllFainted());
    }

    public boolean isNextDay() {
        boolean prevVal = this.isNextDay;
        this.isNextDay = false;
        return prevVal;
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

    public boolean isUpdateEnemyTeam() {
        boolean prevVal = this.isUpdateEnemyTeam;
        this.isUpdateEnemyTeam = false;
        return prevVal;
    }

    public boolean isBattleOccur() {
        boolean prevVal = this.isBattleOccur;
        this.isBattleOccur = false;
        return prevVal;
    }

    public boolean isPlayerWon() {
        boolean prevVal = this.isPlayerWon;
        this.isPlayerWon = false;
        return prevVal;
    }

    public boolean isEnemyWon() {
        boolean prevVal = this.isEnemyWon;
        this.isEnemyWon = false;
        return prevVal;
    }

    public void useItemForMonster(Weapon w, int itemIndex) {
        Random rand = new Random();
        this.playerTeam.useItemForMonster(w, rand.nextInt(this.playerTeam.size()));
        this.playerTeam.getWeaponBag().remove(itemIndex);
        this.isUpdateBag = true;
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }
    public void useItemForMonster(Medicine m, int itemIndex) {
        Random rand = new Random();
        int monsterIndex = this.playerTeam.getLeastHealthMonsterIndex();
        this.playerTeam.useItemForMonster(m, rand.nextInt(this.playerTeam.size()));
        this.playerTeam.getMedicineBag().remove(itemIndex);
        this.isUpdateBag = true;
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }
    public void useItemForMonster(Shield s, int itemIndex) {
        Random rand = new Random();
        int monsterIndex = this.playerTeam.getFirstHealthMonsterIndex();
        this.playerTeam.useItemForMonster(s, rand.nextInt(this.playerTeam.size()));
        this.playerTeam.getShieldBag().remove(itemIndex);
        this.isUpdateBag = true;
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }

    public void isAbleToRefreshAll() {
    	this.isRefreshAllPressed = true;
    	setChanged();
    	notifyObservers();
    }
    
    public boolean isRefreshAllPressed() {
    	boolean prevVal = this.isRefreshAllPressed;
        this.isRefreshAllPressed = false;
        return prevVal;
    }

    public boolean isGameOver() {
        boolean prevVal = this.isGameOver;
        this.isGameOver = false;
        return prevVal;
    }
}
