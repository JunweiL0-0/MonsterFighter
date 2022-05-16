package main.java.model;

import javax.swing.ImageIcon;

public class MedicineBuilderImpl implements MedicineBuilder{
	private String name;
    private int price;
    private int rarity;
    private int effect;
    
    
	public MedicineBuilder name(String name) {
		// TODO Auto-generated method stub
		this.name = name;
		return this;
	}
	public MedicineBuilder price(int price) {
		// TODO Auto-generated method stub
		this.price = price;
		return this;
	}
	public MedicineBuilder rarity(int rarity) {
		// TODO Auto-generated method stub
		this.rarity = rarity;
		return this;
	}
	public MedicineBuilder effect(int effect) {
		// TODO Auto-generated method stub
		this.effect = effect;
		return this;
	}
	public Medicine build() {
		// TODO Auto-generated method stub
		return new Medicine(this.name,this.price,this.effect,this.rarity);
	}


}
