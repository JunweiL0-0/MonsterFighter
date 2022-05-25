package main.java.model;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * The class is used for (randomly) generating monster(s)
 * <br>
 * Note: When constructing a monster, we have to fetch the image first! Otherwise, the monster's name will be random.
 * <br>
 * Note: The rarity level should be consistency with the amount of folder we have in image/monster.
 * <br>
 * Note: If you want to add a new monster into the game. Place its image in the image/monster, the new constructed
 * monster's will be the file(image) name.
 */
public class MonsterGenerator {
    // temporary variable for storing the image's name and assign it to the monster
    private String monsterName;


    /**
     * MonsterGenerator's constructor. Set monster's rarity and name.
     */
    public MonsterGenerator() {
        this.monsterName = "DefaultName";
    }

    /**
     * This is an overloaded method for generateMonster. 
     * If an integer is being passed in while calling this method. It will generate monster(s) and return an arrayList.
     * 
     * @param numOfMonster the amount of monster
     * @return an arrayList which contains the generated(randomly) monsters.
     */
    public ArrayList<Monster> generateMonster(int numOfMonster) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i=0; i<numOfMonster; i++) {
            monsters.add(generateMonster());
        }
        return monsters;
    }

    /**
     * Randomly generate a monster.
     * This function will construct a monster randomly.
     *
     * @return a single randomly generated monster.
     */
    public Monster generateMonster() {
        MonsterBuilderImpl b = new MonsterBuilderImpl();
        // randomly
        int rarity = getRarityRand();
        // price, health, damage, level
        return b.imageIcon(getImageIconForRarityRand(rarity))
                .name(this.monsterName)
                .price(getPriceRand(rarity))
                .maxHealth(getMaxHealthRand(rarity))
                .damage(getDamageRand(rarity))
                .level(1)
                .rarity(rarity)
                .build();
    }

    /**
     * This function is aim to generate the initMonsters to be selected by player at the very begining of the game.
     *
     * @return a list of initMonster
     */
    public ArrayList<Monster> generateInitialMonsters() {
        ArrayList<Monster> initMonsters = new ArrayList<>();
        Monster monster1 = new MonsterBuilderImpl()
                .imageIcon(getImageIconForRarityRand(1))
                .name(this.monsterName).price(100).maxHealth(500).damage(200).level(1).rarity(1)
                .build();
        Monster monster2 = new MonsterBuilderImpl()
                .imageIcon(getImageIconForRarityRand(1))
                .name(this.monsterName).price(100).maxHealth(550).damage(150).level(1).rarity(1)
                .build();
        Monster monster3 = new MonsterBuilderImpl()
                .imageIcon(getImageIconForRarityRand(1))
                .name(this.monsterName).price(100).maxHealth(600).damage(100).level(1).rarity(1)
                .build();
        Monster monster4 = new MonsterBuilderImpl()
                .imageIcon(getImageIconForRarityRand(1))
                .name(this.monsterName).price(100).maxHealth(600).damage(100).level(1).rarity(1)
                .build();
        Monster monster5 = new MonsterBuilderImpl()
                .imageIcon(getImageIconForRarityRand(1))
                .name(this.monsterName).price(100).maxHealth(800).damage(30).level(1).rarity(1)
                .build();
        Collections.addAll(initMonsters, monster1, monster2, monster3, monster4, monster5);
        return initMonsters;
    }

    /*
    private function
     */
    /**
     * Randomly get a rarity.
     * Using Random() to randomly get an integer [0, 101) and used the integer to get a rarity.
     *
     * @return a randomly generated integer
     */
    private int getRarityRand() {
        // [0, 101)
        int num = (new Random()).nextInt(0, 101);
        int rarity = -1;
        if (0 <= num && num <= 50) {
            rarity = 1;
        } else if (51 <= num && num <= 80) {
            rarity = 2;
        } else if (81 <= num && num <= 90) {
            rarity = 3;
        } else if (91 <= num && num <= 95) {
            rarity = 4;
        } else if (96 <= num && num <= 98) {
            rarity = 5;
        } else if (99 <= num && num <= 100) {
            rarity =6;
        }
        return rarity;
    }

    /**
     * Randomly get a price base on the monster's rarity.
     *
     * @param rarity monster's rarity
     * @return an integer which representing monster's price
     */
    private int getPriceRand(int rarity) {
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
			price = ((new Random()).nextInt(81,90))*0.01 * rarity * 200;
		}else if (rarity == 6) {
			price = ((new Random()).nextInt(91,100))*0.01 * rarity * 200;
		}
		
		return (int)price;
    }

    /**
     * Randomly get a MaxHealth base on the monster's rarity.
     *
     * @param rarity monster's rarity
     * @return an integer which representing monster's MaxHealth
     */
    private int getMaxHealthRand(int rarity) {
        return (int)(Math.random() * rarity * 1000 + 1);
    }

    /**
     * Randomly get a Damage base on the monster's rarity.
     *
     * @param rarity monster's rarity
     * @return an integer which representing monster's Damage
     */
    private int getDamageRand(int rarity) {
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
			dmg = ((new Random()).nextInt(81,90))*0.01 * rarity * 100;
		}
		else if(rarity == 6) {
			dmg = ((new Random()).nextInt(91,100))*0.01 * rarity * 100;
		}
		return (int)dmg;
    }

    private ImageIcon getImageIconForRarityRand(int rarity) {
    	Random r = new Random();
    	// create an arrayList for adding the monsterNameFile
    	ArrayList<String> monsterNameFiles = new ArrayList<>();
		try {
			// name.txt contains all the monster's image file name. We read it line by line and store the name into the arrayList
			InputStream in = getClass().getResourceAsStream("/main/java/image/monster/" + rarity + "/name.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nameFile = reader.readLine();
			while (nameFile != null) {
				monsterNameFiles.add(nameFile);
				// read next line
				nameFile = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// if we have any monster image files
		if (monsterNameFiles.size() > 0) {
			String selectedNameFile = monsterNameFiles.get(r.nextInt(0, monsterNameFiles.size()));
			try {
				BufferedImage monsterImage = ImageIO.read(getClass().getResourceAsStream("/main/java/image/monster/" + rarity + "/" + selectedNameFile));
				// remove file extension and assign the file name to the monster
				this.monsterName = selectedNameFile.substring(0, selectedNameFile.lastIndexOf("."));
				return new ImageIcon(monsterImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Monster image file" + selectedNameFile + " not found");
				// create a blank image if we can't find any image
		        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		    	Graphics2D g2 = img.createGraphics();
		    	g2.setColor(new Color(255, 204, 255));
		    	g2.fillRect(0, 0, 48*5, 48*5);
		    	this.monsterName = "DefaultName";
		        return new ImageIcon(img);

			} 
		} else {
	        // create a blank image if we can't find any image
	        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
	    	Graphics2D g2 = img.createGraphics();
	    	g2.setColor(new Color(255, 204, 255));
	    	g2.fillRect(0, 0, 48*5, 48*5);
	    	this.monsterName = "DefaultName";
	        return new ImageIcon(img);
		}
    }
}