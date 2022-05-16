package main.java.model;

import javax.swing.ImageIcon;

public interface MedicineBuilder {
	MedicineBuilder name(String name);
	MedicineBuilder price(int price);
	MedicineBuilder rarity(int rarity);
	MedicineBuilder effect(int effect);
	Medicine build();
}
