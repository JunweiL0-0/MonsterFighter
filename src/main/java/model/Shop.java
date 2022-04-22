package main.java.model;

import java.util.ArrayList;

/**
 * A class representing the shop.
 */
public class Shop {
    private ArrayList<GameItem> itemForSell;
    private Generator generator;


    /**
     * Constructor for Shop
     *
     * @param generator a Generator instance
     * @see Generator
     */
    public Shop(Generator generator) {
        this.generator = generator;
        this.itemForSell = new ArrayList<>();
        reloadMonsters();
    }

    /**
     * Clear all items and reload all items in the shop.
     */
    public void refreshShop() {
        clearShop();
        reloadMonsters();
    }

    /**
     * Add item(GameItem) to the shop
     *
     * @param item a GameItem instance.
     * @see GameItem
     */
    public void addItemForSell(GameItem item) {
        this.itemForSell.add(item);
    }

    /**
     * Return an item and remove it from the shop.
     *
     * @param index an index used to locate the item.
     * @return a GameItem instance
     * @see GameItem
     */
    public GameItem sellItem(int index) {
        GameItem purchasedItem = this.itemForSell.get(index);
        this.itemForSell.remove(index);
        return purchasedItem;
    }

    /* getters go here */
    public ArrayList<GameItem> getItemForSell() {
        return this.itemForSell;
    }

    /* private functions */
    /**
     * Use Generator to generate monsters and add them to the shop.
     */
    private void reloadMonsters() {
        for(int i=0; i<=3; i++) {
            this.itemForSell.add(this.generator.generateMonster());
        }
    }

    /**
     * Remove all items in the shop.
     */
    private void clearShop() {
        this.itemForSell.clear();
    }
}
