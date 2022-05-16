package main.java.model;

import javax.swing.ImageIcon;

public class Weapon extends GameItem {
	
	private int rarity;
	private int dmg;
	private String rarityStr;
	private ImageIcon icon;

	public Weapon(String name, int price, int rarity, int dmg, String rarityStr, ImageIcon icon) {
		super(name, price);
		this.rarity = rarity;
		this.dmg = dmg;
		this.rarityStr = rarityStr;
		this.icon = icon;
	}
	
	public String toString() {
		String weaponName = String.format("Name: %s\n", this.getName());
        String weaponPrice = String.format("Price: %s\n", this.getPrice());
        String weaponRarity = String.format("Rarity: %s\n",this.getRarityStr());
        String weaponDmg = String.format("Dmg: %s\n", this.getDmg());
        String weaponRefundPrice = String.format("RefundPrice: %s\n", this.getRefundPrice());
        return weaponName + weaponRarity +weaponDmg+ weaponPrice + weaponRefundPrice;
	}
	
	public int getRarityNum() {
		return this.rarity;
	}
	
	private int getDmg() {
		return this.dmg;
	}
	
	public String getRarityStr() {
		return this.rarityStr;
	}
	
	public ImageIcon getImageIcon() {
		return this.icon;
	}
	
}
