package main.java.model;


/**
 * A class representing the medicine item.
 */
public class Medicine extends GameItem {
	private int effect;

	/**
	 * Medicine's constructor. It will se the name/price/effect/rarity for the medicine
	 *
	 * @param name a String which represent the name of the medicine
	 * @param price an integer which represent the price of the medicine
	 * @param effect an integer which represent the effect of the medicine
	 * @param rarity an integer which represent the rarity of the medicine
	 */
	public Medicine(String name, int price, int effect, int rarity) {
		super(name, price, rarity);
		this.effect = effect;
	}

	/**
	 * Override the toString() method. A description of the medicine.
	 *
	 * @return a String used to describe the medicine
	 */
	@Override
	public String toString() {
		String medicineName = String.format("%s Potion\n", this.getName());
        String medicinePrice = String.format("Price: %s\n", this.getPrice());
        String medicineEffect = String.format("Effect: +%s Health\n",this.getEffect());
        String medicineRefundPrice = String.format("RefundPrice: %s\n", this.getRefundPrice());
        return medicineName + medicineEffect + medicinePrice + medicineRefundPrice;
	}

	/**
	 * Return the amount of effect.
	 *
	 * @return an integer value which represent the amount of effect of the medicine
	 */
	public int getEffect() {
		return this.effect;
	}
}