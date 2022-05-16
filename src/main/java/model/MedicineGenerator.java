package main.java.model;

import java.util.Random;

public class MedicineGenerator {
	
	private String medicineName;
	private int effect;
	
	
	public MedicineGenerator() {
		this.medicineName = "";
	}
	
	public Medicine generateMedicine() {
		MedicineBuilderImpl m = new MedicineBuilderImpl();
		int rarity = getRarity();
		
		return m.name(this.medicineName)
				.price(getPrice(rarity))
				.effect(this.effect)
				.rarity(rarity)
				.build();
		
		
	}
	
	private int getPrice(int rarity) {
        return (int)(rarity * 50);
    }
	
	
	private int getRarity() {
        // [0, 101)
        int num = (new Random()).nextInt(0, 101);
        int rarity = -1;
        if (0 <= num && num <= 40) {
            rarity = 1;
            this.medicineName ="Common";
            this.effect = 50;
        } else if (41 <= num && num <= 70) {
            rarity = 2;
            this.medicineName ="Uncommon";
            this.effect = 150;

        } else if (71 <= num && num <= 90) {
            rarity = 3;
            this.medicineName ="Rare";
            this.effect = 300;

        } else if (91 <= num && num <= 100) {
            rarity = 4;
            this.medicineName ="Epic";
            this.effect = 500;

        }
		return rarity;
	}
//	private String getMedName() {
//		return this.medicineName;
//	}
//	private int getMedEffect() {
//		return this.effect;
//	}
}
