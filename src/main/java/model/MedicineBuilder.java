package main.java.model;


/**
 * An interface to specifics the functions needed to be implemented when you are creating a new MedicineBuilder class.
 */
public interface MedicineBuilder {
	/**
	 * Store the name of the medicine
	 *
	 * @param name a string represent the name of the medicine
	 * @return the medicineBuilder itself
	 */
	MedicineBuilder name(String name);

	/**
	 * Store the price of the medicine
	 *
	 * @param price an integer represent the price of the medicine
	 * @return the medicineBuilder itself
	 */
	MedicineBuilder price(int price);

	/**
	 * Store the rarity of the medicine
	 *
	 * @param rarity an integer represent the rarity of the medicine
	 * @return the medicineBuilder itself
	 */
	MedicineBuilder rarity(int rarity);

	/**
	 * Store the effect of the medicine
	 *
	 * @param effect an integer represent the amount of effect of the medicine
	 * @return the medicineBuilder itself
	 */
	MedicineBuilder effect(int effect);

	/**
	 * Create a new medicine instance base on the information is has.
	 *
	 * @return a newly created medicine instance
	 */
	Medicine build();
}
