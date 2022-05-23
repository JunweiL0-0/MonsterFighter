package main.java.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class WeaponGenerator {
	private static final String IMAGEPATH = "src/main/java/image/weapon/";
	
	private String weaponName;
	private String rarityStr;
	
	
	public WeaponGenerator() {
		this.weaponName = "";
	}
	
	public Weapon generateWeapon() {
		WeaponBuilderImpl m = new WeaponBuilderImpl();
		int rarity = getRarity();
		
		return m.imageIcon(getImageIconForRarityRand(rarity))
				.name(this.weaponName)
				.price(getPrice(rarity))
				.rarity(rarity)
				.damage(getDamage(rarity))
				.rarityStr(this.rarityStr)
				.build();
	}
	
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
	
	private ImageIcon getImageIconForRarityRand(int rarity) {
//        Random r = new Random();
//        // folder which contains monster's image
//        File imageFolder = new File(IMAGEPATH+rarity);
//        // extract all files in that folder and store them into a list.
//        File[] images = imageFolder.listFiles();
//        if (images != null) {
//            try {
//                // randomly get a imageFile
//                File imageFile = images[r.nextInt(images.length)];
//                // get imageFile's name
//                String imageFileName = imageFile.getName();
//                // remove extension and add it save it
//                // add an if statement to handle the error(File does not contain any extension)
//                if(imageFileName.contains(".")) {
//                    this.weaponName = imageFileName.substring(0, imageFileName.lastIndexOf('.'));
//                }
//                return new ImageIcon(ImageIO.read(imageFile));
//            } catch (IOException e) {
//                // this exception will occur if the IO stream can not be established.
//                e.printStackTrace();
//            }
//        }
        // create a blank image if we can't find any image
        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        return new ImageIcon(img);
    }
}
