package main.java.model;

import javax.swing.ImageIcon;


/**
 * This class is designed for representing the shield in the game
 */
public class Shield extends GameItem{
	private final int shield;
	private final String rarityStr;
	private final ImageIcon icon;


	/**
	 * The constructor of the shield.
	 *
	 * @param name a string represent the name of the shield
	 * @param price an integer represent the price of the shield
	 * @param rarity an integer represent the rarity of the shield
	 * @param shield an integer represent the amount of shield of the shield
	 * @param rarityStr a string represent the rarity of the shield
	 * @param icon an imageIcon
	 */
	public Shield(String name, int price, int rarity, int shield, String rarityStr, ImageIcon icon) {
		super(name, price, rarity);
		this.shield = shield;
		this.rarityStr = rarityStr;
		this.icon = icon;
	}

	/**
	 * Return a description of the shield.
	 *
	 * @return a description of the shield
	 */
	public String toString() {
		String shieldName = String.format("Name: %s\n", this.getName());
        String shieldPrice = String.format("Price: %s\n", this.getPrice());
        String shieldRarity = String.format("Rarity: %s\n",this.getRarityStr());
        String shieldDmg = String.format("Shield: %s\n", this.getShield());
        String shieldRefundPrice = String.format("RefundPrice: %s\n", this.getRefundPrice());
        return shieldName + shieldRarity +shieldDmg+ shieldPrice + shieldRefundPrice;
	}

	/**
	 * Return the amount of shield of the shield
	 *
	 * @return the amount of shield of the shield
	 */
	public int getShield() {
		return this.shield;
	}

	/**
	 * Return the rarityStr of the shield
	 *
	 * @return a string represent the rarity of the shield.
	 */
	public String getRarityStr() {
		return this.rarityStr;
	}

	/**
	 * Return the imageIcon of the shield
	 *
	 * @return an imageIcon
	 */
	public ImageIcon getImageIcon() {
		return this.icon;
	}
}
