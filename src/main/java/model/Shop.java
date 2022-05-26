package main.java.model;

import java.util.ArrayList;

import main.java.controller.GameController;

/**
 * A class representing the shop.
 */
public class Shop {
	private ArrayList<ArrayList<GameItem>> itemForSell;
    private GameController gc;

    /**
     * Constructor for Shop
     *
     * @param gc a Generator instance
     * @see MonsterGenerator
     */
    public Shop(GameController gc) {
    	this.itemForSell = new ArrayList<>();
        this.gc = gc;
    }

    /**
     * Clear all items and reload all items in the shop.
     */
    public void refreshShop() {
        clearShop();
        reloadMonsters();
        reloadWeapons();
        reloadShields();
        reloadMeds();
    }

    /**
     * Return an item and remove it from the shop.
     *
     * @param index an index used to locate the item.
     * @return a GameItem instance
     * @see GameItem
     */
    public GameItem sellItem(int index) {
        GameItem purchasedItem = this.itemForSell.get(index).get(index);
        this.itemForSell.remove(index).remove(index);
        return purchasedItem;
    }

    /* private functions */
    /**
     * Use Generator to generate monsters and add them to the shop.
     */
    private void reloadMonsters() {
    	ArrayList<GameItem> monsters = new ArrayList<>();
    	for(int i = 0; i<5; i++) {
    		monsters.add(i, gc.generateMonster());
    	}

    	this.itemForSell.add(monsters);
    }

    /**
     * Reload the weapons in the shop
     */
    private void reloadWeapons() {
    	ArrayList<GameItem> weapons = new ArrayList<>();
    	for(int i = 0; i<5; i++) {
    		weapons.add(i, gc.generateWeapon());
    	}

    	this.itemForSell.add(weapons);
    }

    /**
     * Reload the shield in the shop
     */
    private void reloadShields() {
    	ArrayList<GameItem> shields = new ArrayList<>();
    	for(int i = 0; i<5; i++) {
    		shields.add(i, gc.generateShield());
    	}
    	this.itemForSell.add(shields);
    }

    /**
     * Reload the medicine in the shop
     */
    private void reloadMeds() {
    	ArrayList<GameItem> potions = new ArrayList<>();
    	for(int i = 0; i<5; i++) {
    		potions.add(i, gc.generateMedicine());
    	}
    	this.itemForSell.add(potions);
    }

    /**
     * Return the monsters which are ready to sell.
     *
     * @return a list of monsters
     */
    public ArrayList<GameItem> getMonstersForSell(){
    	return this.itemForSell.get(0);
    }

    /**
     * Return the weapons which are ready to sell.
     *
     * @return a list of weapons which are ready to sell.
     */
    public ArrayList<GameItem> getWeaponsForSell(){
    	return this.itemForSell.get(1);
    }

    /**
     * Return the shields which are ready to sell.
     *
     * @return a list of shields
     */
    public ArrayList<GameItem> getShieldsForSell(){
    	return this.itemForSell.get(2);
    }

    /**
     * Return the medicines which are ready to sell
     *
     * @return a list of medicines
     */
    public ArrayList<GameItem> getMedsForSell(){
    	return this.itemForSell.get(3);
    }
    
    /**
     * Remove all items in the shop.
     */
    private void clearShop() {
    	itemForSell.clear();
    }
}


