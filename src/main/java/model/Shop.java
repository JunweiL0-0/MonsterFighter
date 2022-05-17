package main.java.model;

import java.util.ArrayList;

import main.java.controller.GameController;

/**
 * A class representing the shop.
 */
public class Shop {
//    private ArrayList<ArrayList<Monster>> itemForSell;
    private GameController gc;
    private ArrayList<Monster> monstersForSell;
    private ArrayList<Weapon> weaponsForSell;
    private ArrayList<Shield> shieldsForSell;
    private ArrayList<Medicine> medsForSell;

    /**
     * Constructor for Shop
     *
     * @param generator a Generator instance
     * @see MonsterGenerator
     */
    public Shop(GameController gc) {
        this.gc = gc;
        this.monstersForSell = new ArrayList<>();
        this.weaponsForSell = new ArrayList<>();
        this.shieldsForSell = new ArrayList<>();
        this.medsForSell = new ArrayList<>();
        refreshShop();
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
//    public GameItem sellItem(int index) {
//        GameItem purchasedItem = this.itemForSell.get(index).get(index);
//        this.itemForSell.remove(index).remove(index);
//        return purchasedItem;
//    }

    /* getters go here */
//    public ArrayList<GameItem> getItemForSell() {
//        return this.itemsForSell;
//    }

    /* private functions */
    /**
     * Use Generator to generate monsters and add them to the shop.
     */
    private void reloadMonsters() {
    	
        for(int i=0; i<=4; i++) {
            this.monstersForSell.add(this.gc.generateMonster());
        }
    }
    
    private void reloadWeapons() {
        for(int i=0; i<=4; i++) {
            this.weaponsForSell.add(this.gc.generateWeapon());
        }
    }
    
    private void reloadShields() {
        for(int i=0; i<=4; i++) {
            this.shieldsForSell.add(this.gc.generateShield());
        }
        
    }
    
    private void reloadMeds() {
        for(int i=0; i<=4; i++) {
        	this.medsForSell.add(gc.generateMedicine());
        }
    }
    
    
    public ArrayList<Monster> getMonstersForSell(){
    	return this.monstersForSell;
    }
    
    public ArrayList<Weapon> getWeaponsForSell(){
    	return this.weaponsForSell;
    }
    
    public ArrayList<Shield> getShieldsForSell(){
    	return this.shieldsForSell;
    	
    }
    
    public ArrayList<Medicine> getMedsForSell(){
    	return this.medsForSell;
    	
    }
    
    public void removeMonster(int i) {
    	this.monstersForSell.remove(i);
    	
    }

    public void removeWeapon(int i) {
    	this.weaponsForSell.remove(i);
    	
    }
    
    public void removeShield(int i) {
    	this.shieldsForSell.remove(i);
    	
    }
    
    public void removeMed(int i) {
    	this.medsForSell.remove(i);
    	
    }
    
    /**
     * Remove all items in the shop.
     */
    private void clearShop() {
        this.monstersForSell.clear();
        this.weaponsForSell.clear();
        this.shieldsForSell.clear();
        this.medsForSell.clear();
    }
 
}


