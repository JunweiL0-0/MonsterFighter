package main.java.model;


/**
 * A class representing the medicine item.
 */
public class Medicine extends GameItem {
	
	private int effect;
	private int rarity;
	
	
	public Medicine(String name, int price, int effect, int rarity) {
		super(name, price);
		this.effect = effect;
		this.rarity = rarity;
	}
	
	public String toString() {
		String medicineName = String.format("%s Potion\n", this.getName());
        String medicinePrice = String.format("Price: %s\n", this.getPrice());
        String medicineEffect = String.format("Effect: +%s Health\n",this.getEffect());
        String medicineRefundPrice = String.format("RefundPrice: %s\n", this.getRefundPrice());
        return medicineName + medicineEffect + medicinePrice + medicineRefundPrice;
	}
	
	
	public int getRarityNum() {
		return this.rarity;
	}
	
	public int getEffect() {
		return this.effect;
	}
	
}