package main.java.model;

import javax.swing.ImageIcon;

public class WeaponBuilderImpl implements WeaponBuilder{
	private String name;
    private int price;
    private int damage;
    private int rarity;
    private String rarityStr;
    private ImageIcon imageIcon;
    
    
	public WeaponBuilder name(String name) {
		// TODO Auto-generated method stub
		this.name = name;
		return this;
	}
	
	public WeaponBuilder price(int price) {
		// TODO Auto-generated method stub
		this.price = price;
		return this;
	}
	
	public WeaponBuilder damage(int damage) {
		// TODO Auto-generated method stub
		this.damage = damage;
		return this;
	}
	
	public WeaponBuilder rarity(int rarity) {
		// TODO Auto-generated method stub
		this.rarity = rarity;
		return this;
	}
	
	public WeaponBuilder rarityStr(String rarityStr) {
		// TODO Auto-generated method stub
		this.rarityStr = rarityStr;
		return this;
	}
	
	public WeaponBuilder imageIcon(ImageIcon imageIcon) {
		// TODO Auto-generated method stub
		this.imageIcon = imageIcon;
		return this;
	}
	
	public Weapon build() {
		// TODO Auto-generated method stub
		return new Weapon(this.name, this.price, this.rarity, this.damage, this.rarityStr, this.imageIcon);
	}
	


}
