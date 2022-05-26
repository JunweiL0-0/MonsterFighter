package main.java.model;

import javax.swing.ImageIcon;

/**
 * A builder implemented the shieldBuilder interface
 */
public class ShieldBuilderImpl implements ShieldBuilder {
	private String name;
    private int price;
    private int shield;
    private int rarity;
    private String rarityStr;
    private ImageIcon imageIcon;


	/**
	 * Store the name of the shield.
	 *
	 * @param name a string represent the name of the shield
	 * @return the builder itself.
	 */
	public ShieldBuilder name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Store the price of the shield
	 *
	 * @param price an integer represent the price of the shield
	 * @return the builder itself.
	 */
	public ShieldBuilder price(int price) {
		this.price = price;
		return this;
	}

	/**
	 * Store the amount of shield the shield has
	 *
	 * @param shield an integer represent the shield of the shield
	 * @return the builder itself.
	 */
	public ShieldBuilder shield(int shield) {
		this.shield = shield;
		return this;
	}

	/**
	 * Store the rarity of the shield
	 *
	 * @param rarity an integer represent the rarity of the shield
	 * @return the builder itself
	 */
	public ShieldBuilder rarity(int rarity) {
		this.rarity = rarity;
		return this;
	}

	/**
	 * Store the rarityStr of the shield.
	 *
	 * @param rarityStr a string represent the rarity of the shield
	 * @return the builder itself.
	 */
	public ShieldBuilder rarityStr(String rarityStr) {
		this.rarityStr = rarityStr;
		return this;
	}

	/**
	 * Return the imageIcon
	 *
	 * @param imageIcon an imageIcon
	 * @return the builder itself.
	 */
	public ShieldBuilder imageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
		return this;
	}

	/**
	 * Return a new shield instance
	 *
	 * @return a new shield instance
	 */
	public Shield build() {
		return new Shield(this.name, this.price, this.rarity, this.shield, this.rarityStr, this.imageIcon);
	}
}
