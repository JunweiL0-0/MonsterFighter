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
     * @param generator a Generator instance
     * @see MonsterGenerator
     */
    public Shop(GameController gc) {
        this.gc = gc;
        this.itemForSell = new ArrayList<ArrayList<GameItem>>(4);
        reloadMonsters();
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
     * Add item(GameItem) to the shop
     *
     * @param item a GameItem instance.
     * @see GameItem
     */
//    public void addItemForSell(GameItem item) {
//        this.itemForSell.add(item);
//    }

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

    /* getters go here */
    public ArrayList<ArrayList<GameItem>> getItemForSell() {
        return this.itemForSell;
    }

    /* private functions */
    /**
     * Use Generator to generate monsters and add them to the shop.
     */
    private void reloadMonsters() {
    	ArrayList<GameItem> monsters = new ArrayList<GameItem>(4);
        for(int i=0; i<=3; i++) {
            monsters.add(this.gc.generateMonster());
        }
        itemForSell.add(0,monsters);
    }
    
    private void reloadWeapons() {
    	ArrayList<GameItem> weapons = new ArrayList<GameItem>(4);
        for(int i=0; i<=3; i++) {
            weapons.add(this.gc.generateMonster());
        }
        itemForSell.add(1,weapons);
    }
    
    private void reloadShields() {
    	ArrayList<GameItem> shields = new ArrayList<GameItem>(4);
        for(int i=0; i<=3; i++) {
            shields.add(this.gc.generateMonster());
        }
        itemForSell.add(2,shields);
    }
    
    private void reloadMeds() {
    	ArrayList<GameItem> meds = new ArrayList<GameItem>(4);
        for(int i=0; i<=3; i++) {
        	meds.add(this.gc.generateMonster());
        }
        itemForSell.add(3, meds);
    }
    
    
    

    /**
     * Remove all items in the shop.
     */
    private void clearShop() {
        this.itemForSell.clear();
    }
}
