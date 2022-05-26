package main.java.model;

import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * This is a class used to randomly generate shield
 */
public class ShieldGenerator {
	private String shieldName;
	private String rarityStr;


	/**
	 * The constructor of the shieldGenerator.
	 */
	public ShieldGenerator() {
		this.shieldName = "";
	}

	/**
	 * Generate the shield randomly.
	 *
	 * @return a newly generated shield instance
	 */
	public Shield generateShield() {
		ShieldBuilderImpl m = new ShieldBuilderImpl();
		int rarity = getRarity();
		
		return m.imageIcon(getImageIconForRarityRand())
				.name(this.shieldName)
				.price(getPrice(rarity))
				.rarity(rarity)
				.shield(getShield(rarity))
				.rarityStr(this.rarityStr)
				.build();
	}

	/**
	 * Randomly get the rarity
	 *
	 * @return the rarity of the shield
	 */
	private int getRarity() {
        // [0, 101)
        int num = (new Random()).nextInt(0, 101);
        int rarity = -1;
        if (0 <= num && num <= 40) {
            rarity = 1;
            this.rarityStr = "Common";
        } else if (41 <= num && num <= 60) {
            rarity = 2;
            this.rarityStr = "Uncommon";
        } else if (61 <= num && num <= 80) {
            rarity = 3;
            this.rarityStr = "Rare";
        } else if (81 <= num && num <= 95) {
            rarity = 4;
            this.rarityStr = "Epic";
        } else if (96 <= num && num <= 100) {
        	rarity = 5;
            this.rarityStr = "Legendary";
        }
		return rarity;
	}

	/**
	 * Return the price of the shield base on its rarity
	 *
	 * @param rarity an integer represent the rarity of the shield
	 * @return an integer represent the price of the shield
	 */
	private int getPrice(int rarity) {
		double price = -1;
		if (rarity == 1) {
			price = ((new Random()).nextInt(10, 30))*0.01 * rarity * 200;
		}else if (rarity == 2){
			price = ((new Random()).nextInt(31, 50))*0.01* rarity * 200;
		}else if (rarity ==3) {
			price = ((new Random()).nextInt(51, 70))* 0.01 * rarity * 200;
		}else if (rarity == 4) {
			price = ((new Random()).nextInt(71, 80)) * 0.01 * rarity * 200;
		}else if (rarity == 5) {
			price = ((new Random()).nextInt(81,100))*0.01 * rarity * 200;
		}
		return (int)price;
    }

	/**
	 * Return the amount of shield the shield has base on its rarity.
	 *
	 * @param rarity an integer represent the rarity of the shield
	 * @return an integer represent the amount of shield
	 */
	private int getShield(int rarity) {
		double shd = -1;
		if (rarity == 1) {
			shd = ((new Random()).nextInt(10, 30))*0.01 * rarity * 100;
		}else if (rarity == 2){
			shd = ((new Random()).nextInt(31, 50))*0.01* rarity * 100;
		}else if (rarity ==3) {
			shd = ((new Random()).nextInt(51, 70))* 0.01 * rarity * 100;
		}else if (rarity == 4) {
			shd = ((new Random()).nextInt(71, 80)) * 0.01 * rarity * 100;
		}else if (rarity == 5) {
			shd = ((new Random()).nextInt(81,100))*0.01 * rarity * 100;
		}
		return (int)shd;
    }

	/**
	 * Create and set the imageIcon for the shield
	 *
	 * @return an imageIcon
	 */
	private ImageIcon getImageIconForRarityRand() {
        // create a blank image if we can't find any image
        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        return new ImageIcon(img);
    }
}
