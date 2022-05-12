package main.java.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * The class is used for (randomly) generating monster(s)
 * <br>
 * Note: When constructing a monster, we have to fetch the image first! Otherwise, the monster's name will be random.
 * <br>
 * Note: The rarity level should be consistence with the amount of folder we have in image/monster.
 * <br>
 * Note: If you want to add a new monster into the game. Place its image in the image/monster, the new constructed
 * monster's will be the file(image) name.
 */
public class MonsterGenerator {
    private static final String IMAGEPATH = "src/main/java/image/monster/";
    // temporary variable for storing the image's name and assign it to the monster
    private String monsterName;


    /**
     * MonsterGenerator's constructor. Set monster's rarity and name.
     */
    public MonsterGenerator() {
        this.monsterName = "DefaultMonsterName";
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
     * This function is aim to generate the initMonsters to be selected by player at the very first of the game.
     *
     * @return a list of initMonster
     */
    // TODO: ASSIGN NAME WITH THE IMAGE
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
        return (int)(Math.random() * rarity * 300 + 1);
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
        return (int)(Math.random() * rarity * 300 + 1);
    }

    private ImageIcon getImageIconForRarityRand(int rarity) {
        Random r = new Random();
        // folder which contains monster's image
        File imageFolder = new File(IMAGEPATH+rarity);
        // extract all files in that folder and store them into a list.
        File[] images = imageFolder.listFiles();
        if (images != null) {
            try {
                // randomly get a imageFile
                File imageFile = images[r.nextInt(images.length)];
                // get imageFile's name
                String imageFileName = imageFile.getName();
                // remove extension and add it save it
                // add an if statement to handle the error(File does not contain any extension)
                if(imageFileName.contains(".")) {
                    this.monsterName = imageFileName.substring(0, imageFileName.lastIndexOf('.'));
                }
                return new ImageIcon(ImageIO.read(imageFile));
            } catch (IOException e) {
                // this exception will occur if the IO stream can not be established.
                e.printStackTrace();
            }
        }
        // create a blank image if we can't find any image
        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        return new ImageIcon(img);
    }
}