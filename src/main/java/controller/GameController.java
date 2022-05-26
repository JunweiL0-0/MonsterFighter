package main.java.controller;

import main.java.model.*;
import main.java.utilities.Observable;
import main.java.view.ChooseMonsterScreen;
import main.java.view.LandingScreen;
import main.java.view.MainScreen;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * A java controller handling the game logic. This class is observable and is observed by MainScreen.
 */
public class GameController extends Observable {
    // classes
    private final MedicineGenerator medicineGenerator;
    private final MonsterGenerator monsterGenerator;
    private final WeaponGenerator weaponGenerator;
    private final ShieldGenerator shieldGenerator;
    private final BattleField battleField;
    private final Team playerTeam;
    private final Shop shop;
    // variables
    private final ArrayList<Team> battles;
    private String playerName;
    private String difficulty;
    private int currentDay;
    private int totalDay;
    private int gold;
    private int point;
    private int battleIndex;
    // used for observers
    private boolean isRefreshAllPressed;
    private boolean isEncounteredBattle;
    private boolean isUpdateEnemyTeam;
    private boolean isBattleOccur;
    private boolean isUpdatePoint;
    private boolean isUpdateGold;
    private boolean isUpdateTeam;
    private boolean isUpdateBag;
    private boolean isPlayerWon;
    private boolean isGameOver;
    private boolean isEnemyWon;
    private boolean isNextDay;
    

    /**
     * Constructor for the GameController.
     * Init all variable to its default value.
     * Note: Default value: Int: -1, is...Update: false, playerTeam: Team with maximum four monsters
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
        this.isUpdateEnemyTeam = false;
        this.isBattleOccur = false;
        this.isRefreshAllPressed = false;
        this.isNextDay = false;
        this.isGameOver = false;
        this.isUpdateBag = false;
        this.isUpdatePoint = false;
        this.isUpdateGold = false;
    }

    /*
    getters go here
     */
    /**
     * This method is designed for the communication between the GamePanel and the MainScreen.
     * The GamePanel will set the encountered battleIndex and the MainScreen will fetch the battle info via this method.
     *
     * @return a battle(a team instance represent enemy)
     */
    public Team getEnemyTeam() {
        return this.battles.get(this.battleIndex);
    }

    /**
     * Return the playerName stored in the gameController.
     *
     * @return the playerName stored in the gameController.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Return the amount of gold the player has.
     *
     * @return the amount of gold the player has.
     */
    public int getGold() {
        return this.gold;
    }

    /**
     * Return the amount of point the player has.
     *
     * @return the amount of point the player has
     */
    public int getPoint() {
        return this.point;
    }

    /**
     * Return currentDay.
     *
     * @return an integer represents currentDay.
     */
    public int getCurrentDay() {
        return this.currentDay;
    }

    public Shop getShop() {
        return this.shop;
    }

    /**
     * Return the amount of totalDay
     *
     * @return an integer represents totalDay
     */
    public int getTotalDay() {
        return this.totalDay;
    }

    /**
     * Return the player's monster which is ready for attack/defense in this round.
     *
     * @return a player's monster which is ready for attack/defense in this round.
     */
    public Monster getPlayerTeamReadyMonster() {
        return this.battleField.getPlayerTeamReadyMonster();
    }

    /**
     * Return the enemy's monster which is ready for attack/defense in this round.
     *
     * @return an enemy's monster which is ready for attack/defense in this round.
     */
    public Monster getEnemyTeamReadyMonster() {
        return this.battleField.getEnemyTeamReadyMonster();
    }

    /**
     * Return the player's monster which is ready for attack/defense in this round.
     *
     * @return a player's monster which is ready for attack/defense in this round.
     */
    public ArrayList<Monster> getMonsterTeamMember() {
        return this.playerTeam.getTeamMember();
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

    /**
     * Return a player's monster base on the given index.
     *
     * @param i the index of the monster in the team
     * @return a monster instance
     */
    public Monster getMonsterFromTeamByIndex(int i) {
        return this.playerTeam.getMonsterByIndex(i);
    }

    /**
     * Return an arrayList represent the player's monster bag.
     *
     * @return an arrayList represent the player's monster bag.
     */
    public ArrayList<Monster> getMonsterBag() {
        return this.playerTeam.getMonsterBag();
    }

    /**
     * Return an arrayList represent the player's weapon bag.
     *
     * @return an arrayList represent the player's weapon bag.
     */
    public ArrayList<Weapon> getWeaponBag() {
        return this.playerTeam.getWeaponBag();
    }

    /**
     * Return an arrayList represent the player's shield bag.
     *
     * @return an arrayList represent the player's shield bag.
     */
    public ArrayList<Shield> getShieldBag() {
        return this.playerTeam.getShieldBag();
    }

    /**
     * Return an arrayList represent the player's medicine bag.
     *
     * @return an arrayList represent the player's medicine bag.
     */
    public ArrayList<Medicine> getMedicineBag() {
        return this.playerTeam.getMedicineBag();
    }

    /**
     * Return the amount of the total battle.
     *
     * @return an arrayList represent the player's monster bag.
     */
    public int getTotalBattle() {
        return this.battles.size();
    }

    /**
     * Return the index of the current encountered battle
     *
     * @return an integer represent the index of the battle
     */
    public int getBattleIndex() {
        return this.battleIndex;
    }
    
    public int getPlayerTeamSize() {
    	return this.playerTeam.size();
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

    /* Observer */
    /**
     * Trigger an attack or defence while we are in a battle and notify the observers.
     */
    public void battle() {
        // 1: player won, -1: enemy Won, 0: nobody won yet
        int result = this.battleField.battle();
        if (result == 1) {
            // increase exp by 50
            for (Monster monster :playerTeam.getTeamMember()) {
                monster.incrementExpBy(50);
            }
            this.gold += 100;
            this.point += 100;
            this.isPlayerWon = true;
            this.battles.remove(this.battleIndex);
            this.battleIndex = -1;
            existBattle();
        } else if (result == (-1)) {
            this.isEnemyWon = true;
            enterBattle();
        } else {
            this.isBattleOccur = true;
        }
        this.isUpdateTeam = true;
        this.isUpdateEnemyTeam = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Consume the item and increase/decrease the properties of a monster. This is an overloading function.
     *
     * @param w a weapon instance
     * @param itemIndex the index of weapon in the bag
     */
    public void useItemForMonster(Weapon w, int itemIndex) {
        Random rand = new Random();
        this.playerTeam.useItemForMonster(w, rand.nextInt(this.playerTeam.size()));
        this.playerTeam.getWeaponBag().remove(itemIndex);
        this.isUpdateBag = true;
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }
    
    public void removeMonsterFromLeftPanel(int index) {
    	this.playerTeam.removeMonsterFromTeam(index);
    	this.isUpdateTeam = true;
    	this.isUpdateBag = true;
    	setChanged();
    	notifyObservers();
    }

    /**
     * Consume the item and increase/decrease the properties of a monster. This is an overloading function.
     *
     * @param m a medicine instance.
     * @param itemIndex the index of the medicine in the bag
     */
    public void useItemForMonster(Medicine m, int itemIndex) {
        Random rand = new Random();
        this.playerTeam.useItemForMonster(m, rand.nextInt(this.playerTeam.size()));
        this.playerTeam.getMedicineBag().remove(itemIndex);
        this.isUpdateBag = true;
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Consume the item and increase/decrease the properties of a monster. This is an overloading function
     *
     * @param s a shield instance.
     * @param itemIndex the index of the shield in the bag
     */
    public void useItemForMonster(Shield s, int itemIndex) {
        Random rand = new Random();
        this.playerTeam.useItemForMonster(s, rand.nextInt(this.playerTeam.size()));
        this.playerTeam.getShieldBag().remove(itemIndex);
        this.isUpdateBag = true;
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Enter the battle and notify the observers.
     */
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

    /**
     * Add item to player's monster bag and notify the observers. This is an overloading method.
     *
     * @param m a monster instance
     */
    public void addItemToBag(Monster m) {
        this.playerTeam.addItemToMonsterBag(m);
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Add item to player's weapon bag and notify the observers. This is an overloading method.
     *
     * @param w a weapon instance
     */
    public void addItemToBag(Weapon w) {
        this.playerTeam.addItemToWeaponBag(w);
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Add item to player's shield bag and notify the observers. This is an overloading method.
     *
     * @param s a shield instance
     */
    public void addItemToBag(Shield s) {
        this.playerTeam.addItemToShieldBag(s);
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Add item to player's medicine bag and notify the observers. This is an overloading method.
     *
     * @param m a medicine instance
     */
    public void addItemToBag(Medicine m) {
        this.playerTeam.addItemToMedicineBag(m);
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Select a monster and put it into the player's team and notify the observers.
     *
     * @param bagMonsterIndex an index represent the index of the monster in the bag
     */
    public void addBagMonsterToTeam(int bagMonsterIndex) {
        this.playerTeam.addBagMonsterToTeam(bagMonsterIndex);
        this.isUpdateTeam = true;
        this.isUpdateBag = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Sell a monster to the shop and notify the observers. This is an overloading method.
     *
     * @param m a monster instance
     * @param monsterIndex the index of the monster in the bag
     */
    public void sellItem(Monster m, int monsterIndex) {
        this.gold += m.getRefundPrice();
        this.playerTeam.getMonsterBag().remove(monsterIndex);
        this.isUpdateBag = true;
        this.isUpdateGold = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Sell a medicine to the shop and notify the observers. This is an overloading method.
     *
     * @param m a medicine instance
     * @param medicineIndex the index of the medicine in the bag
     */
    public void sellItem(Medicine m, int medicineIndex) {
        this.gold += m.getRefundPrice();
        this.playerTeam.getMedicineBag().remove(medicineIndex);
        this.isUpdateBag = true;
        this.isUpdateGold = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Sell a monster to the shop and notify the observers. This is an overloading method.
     *
     * @param w a weapon instance
     * @param weaponIndex the index of the weapon in the bag
     */
    public void sellItem(Weapon w, int weaponIndex) {
        this.gold += w.getRefundPrice();
        this.playerTeam.getWeaponBag().remove(weaponIndex);
        this.isUpdateBag = true;
        this.isUpdateGold = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Sell a shield to the shop and notify the observers. This is an overloading method.
     *
     * @param s a shield instance
     * @param shieldIndex the index of the shield in the bag
     */
    public void sellItem(Shield s, int shieldIndex) {
        this.gold += s.getRefundPrice();
        this.playerTeam.getShieldBag().remove(shieldIndex);
        this.isUpdateBag = true;
        this.isUpdateGold = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Exist the battle and notify the observers.
     */
    public void existBattle() {
        this.battleField.endBattle();
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Go to next day and notify the observers.
     */
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

    /**
     * Rename the player's monster name based on the given monsterIndex and newName
     * This method will notify the observers
     *
     * @param monsterIndex an integer represent the index of the monster which is about to change name
     * @param newName the new name of the monster
     */
    public void renameTeamMonsterByIndex(int monsterIndex, String newName) {
        this.playerTeam.renameMonsterByIndex(monsterIndex, newName);
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }

    /**
     * This method will be triggered everytime the player encounter an enemy in the gamePanel.
     * This method will notify the observers.
     *
     * @param battleIndex the index of the encountered battle.
     */
    public void storeBattleIndex(int battleIndex) {
        if (this.battleIndex != battleIndex) {
            this.battleIndex = battleIndex;
            this.isEncounteredBattle = true;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Swap two monsters in the team base on the given two index.
     * This method will notify the observers.
     *
     * @param monsterIndex1 the index of the monster which is ready to swap
     * @param monsterIndex2 the index of the monster which is ready to swap
     */
    public void swapMonsterInPlayerTeam(int monsterIndex1, int monsterIndex2) {
        this.playerTeam.swapMonster(monsterIndex1, monsterIndex2);
        this.isUpdateTeam = true;
        setChanged();
        notifyObservers();
    }

    /**
     * Return true if the bag is being updated. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isUpdateBag() {
        boolean prevVal = this.isUpdateBag;
        this.isUpdateBag = false;
        return prevVal;
    }

    /**
     * Return true if the gold is being updated. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isUpdateGold() {
        boolean prevVal = this.isUpdateGold;
        this.isUpdateGold = false;
        return prevVal;
    }

    /**
     * Return true if the point is being updated. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isUpdatePoint() {
        boolean prevVal = this.isUpdatePoint;
        this.isUpdatePoint = false;
        return prevVal;
    }

    /**
     * Return true if we go to next day. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isNextDay() {
        boolean prevVal = this.isNextDay;
        this.isNextDay = false;
        return prevVal;
    }

    /**
     * Return true if we are able to reorder the player team. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isAbleToReorderTeam() {
        return this.playerTeam.size() != 0;
    }

    /**
     * Return true if we encounter a battle. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isEncounteredBattle() {
        boolean prevVal = this.isEncounteredBattle;
        this.isEncounteredBattle = false;
        return prevVal;
    }

    /**
     * Return true if the battle is on player's turn. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isPlayerTurnBattle() {
        return this.battleField.isPlayerTurn();
    }

    /**
     * Return true if the player is able to start a fight(battle). Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isAbleToStartFight() {
        return this.battleIndex != -1 && this.playerTeam.size() != 0 && !(this.playerTeam.isAllFainted());
    }

    /**
     * Return true if the team is being updated. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isUpdateTeam() {
        boolean prevVal = this.isUpdateTeam;
        this.isUpdateTeam = false;
        return prevVal;
    }

    /**
     * Return true if the (enemyTeam)/(encountered battle) is being updated. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isUpdateEnemyTeam() {
        boolean prevVal = this.isUpdateEnemyTeam;
        this.isUpdateEnemyTeam = false;
        return prevVal;
    }

    /**
     * Return true if a battle is occurred. Otherwise, false.
     * Battle occur means either the player's monster attack the enemy's monster or the enemy's monster attack the player's monster.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isBattleOccur() {
        boolean prevVal = this.isBattleOccur;
        this.isBattleOccur = false;
        return prevVal;
    }

    /**
     * Return true if the player won a battle. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isPlayerWon() {
        boolean prevVal = this.isPlayerWon;
        this.isPlayerWon = false;
        return prevVal;
    }

    /**
     * Return true if the enemy won a battle. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isEnemyWon() {
        boolean prevVal = this.isEnemyWon;
        this.isEnemyWon = false;
        return prevVal;
    }

    /**
     * Return true if the refreshAll btn in the shop is being pressed. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isRefreshAllPressed() {
    	boolean prevVal = this.isRefreshAllPressed;
        this.isRefreshAllPressed = false;
        return prevVal;
    }

    /**
     * Return true if the game is over. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     *
     * @return a boolean value which tells us whether an update occur.
     */
    public boolean isGameOver() {
        boolean prevVal = this.isGameOver;
        this.isGameOver = false;
        return prevVal;
    }

    /**
     * Return true if the player won a battle. Otherwise, false.
     * No matter what is the returned value, the value of the variable will always being set to false.
     */
    public void isAbleToRefreshAll() {
        this.isRefreshAllPressed = true;
        setChanged();
        notifyObservers();
    }

    /* Other functions */
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

    /**
     * Generate and return a monster instance.
     *
     * @return a randomly generated monster instance.
     */
    public Monster generateMonster() {
        return monsterGenerator.generateMonster();
    }

    /**
     * Generate and return a medicine instance.
     *
     * @return a randomly generated medicine instance
     */
    public Medicine generateMedicine() {
        return medicineGenerator.generateMedicine();
    }

    /**
     * Generate and return a weapon instance
     *
     * @return a randomly generated weapon instance.
     */
    public Weapon generateWeapon() {
        return weaponGenerator.generateWeapon();
    }

    /**
     * Generate and return a shield instance.
     *
     * @return a randomly generated shield instance
     */
    public Shield generateShield() {
        return shieldGenerator.generateShield();
    }

    /**
     * Randomly generate random events. Probabilities are evenly distributed.
     * Random events include:
     * 1. A random monster join the player team.
     * 2. The leastHealth monster leave from the team.
     * 3. A monster in the team level up.
     */
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

    /**
     * A function used to start the game. Launch the landing screen.
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
        this.playerTeam.getTeamMember().clear();
        this.playerTeam.emptybag();
        this.battles.clear();
        this.battleIndex = -1;
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

    /**
     * Set currentDay to day one and set the totalDay according to the selected difficulty.
     */
    private void initCurrAndTotalDay() {
        this.currentDay = 1;
        if (isEasyMode()) {
            this.totalDay = 10;
        } else if (isHardMode()) {
            this.totalDay = 5;
        }
    }

    /**
     * Set the init gold for the player.
     */
    private void initGold() {
        if (isEasyMode()) {
            this.gold = 500;
        } else if (isHardMode()) {
            this.gold = 200;
        }
    }

    /**
     * Set the init point for the player.
     */
    private void initPoint() {
        if (isEasyMode()) {
            this.point = 500;
        } else if (isHardMode()) {
            this.point = 600;
        }
    }

    /**
     * Return the amount of battles left.
     *
     * @return the amount of battles left.
     */
    public int battleLeft() {
        return this.battles.size();
    }

    /**
     * Init each day's battles. The amount of battles and monsters will vary depends on the currentDay.
     */
    private void initBattles() {
        int totalBattle;
        int monsterPerTeam;
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
        // generate battles
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
}
