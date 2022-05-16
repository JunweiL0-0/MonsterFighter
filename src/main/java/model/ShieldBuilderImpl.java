package main.java.model;

import javax.swing.ImageIcon;

public class ShieldBuilderImpl implements ShieldBuilder {
	private String name;
    private int price;
    private int shield;
    private int rarity;
    private String rarityStr;
    private ImageIcon imageIcon;
    
    
	public ShieldBuilder name(String name) {
		// TODO Auto-generated method stub
		this.name = name;
		return this;
	}
	
	public ShieldBuilder price(int price) {
		// TODO Auto-generated method stub
		this.price = price;
		return this;
	}
	
	public ShieldBuilder shield(int shield) {
		// TODO Auto-generated method stub
		this.shield = shield;
		return this;
	}
	
	public ShieldBuilder rarity(int rarity) {
		// TODO Auto-generated method stub
		this.rarity = rarity;
		return this;
	}
	
	public ShieldBuilder rarityStr(String rarityStr) {
		// TODO Auto-generated method stub
		this.rarityStr = rarityStr;
		return this;
	}
	
	public ShieldBuilder imageIcon(ImageIcon imageIcon) {
		// TODO Auto-generated method stub
		this.imageIcon = imageIcon;
		return this;
	}
	
	public Shield build() {
		// TODO Auto-generated method stub
		return new Shield(this.name, this.price, this.rarity, this.shield, this.rarityStr, this.imageIcon);
	}
}
