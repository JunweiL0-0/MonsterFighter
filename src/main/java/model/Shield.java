package main.java.model;

import javax.swing.ImageIcon;

public class Shield extends GameItem{
	private int shield;
	private String rarityStr;
	private ImageIcon icon;

	public Shield(String name, int price, int rarity, int shield, String rarityStr, ImageIcon icon) {
		super(name, price, rarity);
		this.shield = shield;
		this.rarityStr = rarityStr;
		this.icon = icon;
	}
	
	public String toString() {
		String shieldName = String.format("Name: %s\n", this.getName());
        String shieldPrice = String.format("Price: %s\n", this.getPrice());
        String shieldRarity = String.format("Rarity: %s\n",this.getRarityStr());
        String shieldDmg = String.format("Shield: %s\n", this.getShield());
        String shieldRefundPrice = String.format("RefundPrice: %s\n", this.getRefundPrice());
        return shieldName + shieldRarity +shieldDmg+ shieldPrice + shieldRefundPrice;
	}

	public int getShield() {
		return this.shield;
	}
	
	public String getRarityStr() {
		return this.rarityStr;
	}
	
	public ImageIcon getImageIcon() {
		return this.icon;
	}
}
