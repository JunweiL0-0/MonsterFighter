package main.java.model;


/**
 * An interface to specifics the functions needed to be implemented when you are creating a new MedicineBuilder class.
 */
public interface MedicineBuilder {
	MedicineBuilder name(String name);
	MedicineBuilder price(int price);
	MedicineBuilder rarity(int rarity);
	MedicineBuilder effect(int effect);
	Medicine build();
}
