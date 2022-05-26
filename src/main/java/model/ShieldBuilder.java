package main.java.model;

import javax.swing.ImageIcon;

/**
 * This is an interface used to guild the implementation of the shieldBuilder
 */
public interface ShieldBuilder {
    /**
     * Store the name of the shield
     *
     * @param name a string represent the name of the shield
     * @return the shieldBuilder itself
     */
	ShieldBuilder name(String name);

    /**
     * Store the price of the shield
     *
     * @param price an integer represent the price of the shield
     * @return the shieldBuilder itself
     */
    ShieldBuilder price(int price);

    /**
     * Store the amount of shield of the shield
     *
     * @param shield an integer represent the shield of the shield
     * @return the shieldBuilder itself
     */
    ShieldBuilder shield(int shield);

    /**
     * Store the rarity of the shield
     *
     * @param rarity an integer represent the rarity of the shield
     * @return the shieldBuilder itself
     */
    ShieldBuilder rarity(int rarity);

    /**
     * Store the rarityStr of the shield
     *
     * @param rarityStr a string represent the rarity of the shield
     * @return the shieldBuilder itself
     */
    ShieldBuilder rarityStr(String rarityStr);

    /**
     * Store the imageIcon of the shield
     *
     * @param imageIcon an imageIcon
     * @return the shieldBuilder itself
     */
    ShieldBuilder imageIcon(ImageIcon imageIcon);

    /**
     * Create a shield instance base on the information the builder has.
     *
     * @return a newly created shield instance
     */
    Shield build();
}
