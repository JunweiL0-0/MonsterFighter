package main.java.model;

import javax.swing.ImageIcon;

/**
 * An interface used to guild the creation of a weaponBuilder
 */
public interface WeaponBuilder {
    /**
     * Store the name of the weapon
     *
     * @param name a string represent the name of the weapon
     * @return the weaponBuilder itself
     */
	WeaponBuilder name(String name);

    /**
     * Store the price of the weapon
     *
     * @param price an integer represent the price of the weapon
     * @return the weaponBuilder itself
     */
    WeaponBuilder price(int price);

    /**
     * Store the "damage" of the weapon
     *
     * @param damage an integer represent the "damage" of the weapon
     * @return the weaponBuilder itself
     */
    WeaponBuilder damage(int damage);

    /**
     * Store the rarity of the weapon
     *
     * @param rarity an integer represent the rarity of the weapon
     * @return the weaponBuilder itself
     */
    WeaponBuilder rarity(int rarity);

    /**
     * Store the rarityStr of the weapon
     *
     * @param rarityStr a string represent the rarity of the weapon
     * @return the weaponBuilder itself
     */
    WeaponBuilder rarityStr(String rarityStr);

    /**
     * Store the imageIcon of the weapon
     *
     * @param imageIcon an imageIcon
     * @return the weaponBuilder itself
     */
    WeaponBuilder imageIcon(ImageIcon imageIcon);

    /**
     * Create a weapon instance base on the information it has
     *
     * @return a newly created weapon instance
     */
    Weapon build();
}
