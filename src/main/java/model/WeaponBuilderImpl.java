package main.java.model;

import javax.swing.ImageIcon;

/**
 * This class implement the weaponBuilder interface.
 * By using the builder pattern, we will be build our complex object in an easy and tidy way.
 */
public class WeaponBuilderImpl implements WeaponBuilder {
	private String name;
    private int price;
    private int damage;
    private int rarity;
    private String rarityStr;
    private ImageIcon imageIcon;


	/**
	 * Store the name of the weapon.
	 *
	 * @param name a string represent the name of the weapon
	 * @return the weaponBuilder itself
	 */
	public WeaponBuilder name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Store the price of the weapon.
	 *
	 * @param price an integer represent the price of the weapon
	 * @return the weaponBuilder itself
	 */
	public WeaponBuilder price(int price) {
		this.price = price;
		return this;
	}

	/**
	 * Store the "damage" of the weapon.
	 *
	 * @param damage an integer represent the "damage" of the weapon
	 * @return the weaponBuilder itself
	 */
	public WeaponBuilder damage(int damage) {
		this.damage = damage;
		return this;
	}

	/**
	 * Store the "rarity" of the weapon.
	 *
	 * @param rarity an integer represent the "rarity" of the weapon
	 * @return the weaponBuilder itself
	 */
	public WeaponBuilder rarity(int rarity) {
		this.rarity = rarity;
		return this;
	}

	/**
	 * Store the "rarityStr" of the weapon.
	 *
	 * @param rarityStr a string represent the "rarity" of the weapon
	 * @return the weaponBuilder itself
	 */
	public WeaponBuilder rarityStr(String rarityStr) {
		this.rarityStr = rarityStr;
		return this;
	}

	/**
	 * Store the "imageIcon" of the weapon.
	 *
	 * @param imageIcon an image of the weapon
	 * @return the weaponBuilder itself
	 */
	public WeaponBuilder imageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
		return this;
	}
	
	public Weapon build() {
		return new Weapon(this.name, this.price, this.rarity, this.damage, this.rarityStr, this.imageIcon);
	}
	


}
