package main.java.model;

import javax.swing.*;

/**
 * This is an interface which exposes all methods required for an implementation of a builder to create the monster class
 */
public interface MonsterBuilder {
    /**
     * Store the name of the monster
     *
     * @param name a string represent the name of the monster
     * @return the builder itself
     */
    MonsterBuilder name(String name);

    /**
     * Store the price of the monster
     *
     * @param price an integer represent the price of the monster
     * @return the builder itself
     */
    MonsterBuilder price(int price);

    /**
     * Store the maxHealth of the monster
     *
     * @param maxHealth an integer represent the maxHealth of the monster
     * @return the builder itself
     */
    MonsterBuilder maxHealth(int maxHealth);

    /**
     * Store the "damage" of the monster
     *
     * @param damage an integer represent the "damage" of the monster
     * @return the builder itself
     */
    MonsterBuilder damage(int damage);

    /**
     * Store the level of the monster
     *
     * @param level an integer represent the level of the monster
     * @return the builder itself
     */
    MonsterBuilder level(int level);

    /**
     * Store the rarity of the monster
     *
     * @param rarity an integer represent the rarity of the monster
     * @return the builder itself
     */
    MonsterBuilder rarity(int rarity);

    /**
     * Store the imageIcon of the monster
     *
     * @param imageIcon an imageIcon
     * @return the builder itself
     */
    MonsterBuilder imageIcon(ImageIcon imageIcon);

    /**
     * Create a new monster instance base on the information it has.
     *
     * @return a newly created monster instance
     */
    Monster build();
}
	