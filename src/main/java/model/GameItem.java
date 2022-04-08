package main.java.model;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.max;

/**
 * The class is used for creating a gameItem.
 *
 * @see Monster
 * @see Medicine
 */
public class GameItem {
    public static final AtomicInteger COUNT = new AtomicInteger(0);
    private final int id;
    private String name;
    private int price;
    private int refundPrice;


    /**
     * The constructor for GameItems.
     * @param name Item's name
     * @param price Item's price
     */
    public GameItem(String name, int price) {
        this.name = name;
        this.price = price;
        initRefundPrice(price);
        // increment the counter and assign a unique id to each single item
        this.id = COUNT.incrementAndGet();
    }

    /* getters go here */
    /**
     * Return the item's unique id.
     *
     * @return Unique item id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Return the item's name.
     *
     * @return Item's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the item's price.
     *
     * @return Item's price
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Return the item's refund price.
     *
     * @return Item's refund price if the user wants to refund this item
     */
    public int getRefundPrice() {
        return this.refundPrice;
    }

    /* setters go here */

    /* private functions */
    /**
     * This function will init the refundPrice for each single item if the user wants to refund the item.
     * The refundPrice is equals to price-50. If item's price is lower than 50, the refundPrice will be set to 0;
     *
     * @param price item's sell price
     */
    private void initRefundPrice(int price) {
        this.refundPrice = max(0, this.price-50);
    }
}
