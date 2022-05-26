package main.java.model;

import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * A generator used to randomly generate the weapon
 */
public class WeaponGenerator {
	private String weaponName;
	private String rarityStr;


	/**
	 * The constructor of the weaponGenerator. It will set the weaponName to an empty string.
	 */
	public WeaponGenerator() {
		this.weaponName = "";
	}

	/**
	 * Randomly generate the weapon by using the weaponBuilder.
	 *
	 * @return a randomly generated weapon instance.
	 */
	public Weapon generateWeapon() {
		WeaponBuilderImpl m = new WeaponBuilderImpl();
		int rarity = getRarity();
		
		return m.imageIcon(getImageIconForRarityRand())
				.name(this.weaponName)
				.price(getPrice(rarity))
				.rarity(rarity)
				.damage(getDamage(rarity))
				.rarityStr(this.rarityStr)
				.build();
	}

	/**
	 * Randomly get the weapon's rarity
	 *
	 * @return an integer represent the rarity of the weapon.
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
	 * Randomly generate the price of the weapon base on its rarity.
	 *
	 * @param rarity an integer represent the rarity of the weapon
	 * @return an integer represent the price of the weapon.
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
	 * Randomly generate the "damage" of the weapon base on its rarity.
	 *
	 * @param rarity an integer represent the rarity of the weapon.
	 * @return a randomly generated integer represent the "damage" of the weapon
	 */
	private int getDamage(int rarity) {
		double dmg = -1;
		if (rarity == 1) {
			dmg = ((new Random()).nextInt(10, 30))*0.01 * rarity * 100;
		}else if (rarity == 2){
			dmg = ((new Random()).nextInt(31, 50))*0.01* rarity * 100;
		}else if (rarity ==3) {
			dmg = ((new Random()).nextInt(51, 70))* 0.01 * rarity * 100;
		}else if (rarity == 4) {
			dmg = ((new Random()).nextInt(71, 80)) * 0.01 * rarity * 100;
		}else if (rarity == 5) {
			dmg = ((new Random()).nextInt(81,100))*0.01 * rarity * 100;
		}
		return (int)dmg;
    }

	/**
	 * Crate and return the imageIcon for each of the weapons.
	 *
	 * @return an imageIcon
	 */
	private ImageIcon getImageIconForRarityRand() {
		// create a blank image
        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        return new ImageIcon(img);
    }
}
