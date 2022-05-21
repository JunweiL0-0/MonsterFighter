package main.java.model;

import static java.lang.Math.max;

/**
 * The class is used for creating a gameItem.
 *
 * @see Monster
 * @see Medicine
 */
public class GameItem {
    private String name;
    private int price;
    private int rarity;


    /**
     * The constructor for GameItems.
     * @param name Item's name
     * @param price Item's price
     */
    public GameItem(String name, int price, int rarity) {
        this.name = name;
        this.price = price;
        this.rarity = rarity;
    }

    /* getters go here */
    /**
     * Return the item's name.
     *
     * @return Item's name(String)
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the item's price.
     *
     * @return Item's price(int)
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Return the item's refund price.
     *
     * @return Item's refund price if the user wants to refund this item(int)
     */
    public int getRefundPrice() {
        return max(0, this.price-50);
    }

    /**
     * Return the item's rarity.
     *
     * @return Item's rarity(int)
     */
    public int getRarity() {
        return this.rarity;
    }

    /* setters go here */
    /**
     * Set the new name to current monster.
     *
     * @param name a new name for this monster
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the new price to current monster.
     *
     * @param price a new price for this monster
     */
    public void setPrice(int price) {
        this.price = price;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
}
