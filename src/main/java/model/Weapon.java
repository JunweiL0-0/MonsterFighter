package main.java.model;

import javax.swing.ImageIcon;

/**
 * A class represent the weapon in the game
 */
public class Weapon extends GameItem {
	private final int dmg;
	private final String rarityStr;
	private final ImageIcon icon;


	/**
	 * The constructor of the weapon.
	 *
	 * @param name a string represent the name of the weapon
	 * @param price an integer represent the price of the weapon
	 * @param rarity an integer represent the rarity of the weapon
	 * @param dmg an integer represent the "damage" of the weapon
	 * @param rarityStr a string represent the rairity of the weapon
	 * @param icon an imageIcon
	 */
	public Weapon(String name, int price, int rarity, int dmg, String rarityStr, ImageIcon icon) {
		super(name, price, rarity);
		this.dmg = dmg;
		this.rarityStr = rarityStr;
		this.icon = icon;
	}

	/**
	 * Return a description of the weapon
	 *
	 * @return a description of the weapon
	 */
	public String toString() {
		String weaponName = String.format("Name: %s\n", this.getName());
        String weaponPrice = String.format("Price: %s\n", this.getPrice());
        String weaponRarity = String.format("Rarity: %s\n",this.getRarityStr());
        String weaponDmg = String.format("Dmg: %s\n", this.getDmg());
        String weaponRefundPrice = String.format("RefundPrice: %s\n", this.getRefundPrice());
        return weaponName + weaponRarity +weaponDmg+ weaponPrice + weaponRefundPrice;
	}

	/**
	 * Return the amount of damage the weapon has.
	 *
	 * @return an integer represent the amount of damage the weapon has.
	 */
	public int getDmg() {
		return this.dmg;
	}

	/**
	 * Return a string represent the rarity of the weapon.
	 *
	 * @return a string represent the rarity of the weapon
	 */
	public String getRarityStr() {
		return this.rarityStr;
	}

	/**
	 * Return the imageIcon of the weapon
	 *
	 * @return an imageIcon
	 */
	public ImageIcon getImageIcon() {
		return this.icon;
	}
}
