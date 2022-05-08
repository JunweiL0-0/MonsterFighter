package main.java.model;

import java.util.*;


/**
 * The class is used for (randomly) generating gameItem base on the data provided by ItemDataStorage.
 *
 */
public class MonsterGenerator {
    // {rarity: [name1, name2], rarity2: .... }
    private final Map<Integer, List<String>> mRarityAndName;


    public MonsterGenerator() {
        this.mRarityAndName = new HashMap<>();
        this.mRarityAndName.put(1, Arrays.asList("Monster1", "Monster11"));
        this.mRarityAndName.put(2, Arrays.asList("Monster2", "Monster22"));
        this.mRarityAndName.put(3, Arrays.asList("Monster3", "Monster33"));
        this.mRarityAndName.put(4, Arrays.asList("Monster4", "Monster44"));
        this.mRarityAndName.put(5, Arrays.asList("Monster5", "Monster55"));
        this.mRarityAndName.put(6, Arrays.asList("Monster6", "Monster66"));
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
        String monsterName = getNameRand(rarity);
        // price, health, damage, level
        return b.name(monsterName)
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
    public ArrayList<Monster> generateInitialMonsters() {
        ArrayList<Monster> initMonsters = new ArrayList<>();
        Monster monster1 = new MonsterBuilderImpl()
                .name("DefMonster1").price(100).maxHealth(500).damage(200).level(1).rarity(1).build();
        Monster monster2 = new MonsterBuilderImpl()
                .name("DefMonster2").price(100).maxHealth(550).damage(150).level(1).rarity(1).build();
        Monster monster3 = new MonsterBuilderImpl()
                .name("DefMonster3").price(100).maxHealth(600).damage(100).level(1).rarity(1).build();
        Monster monster4 = new MonsterBuilderImpl()
                .name("DefMonster4").price(100).maxHealth(600).damage(100).level(1).rarity(1).build();
        Monster monster5 = new MonsterBuilderImpl()
                .name("DefMonster5").price(100).maxHealth(800).damage(30).level(1).rarity(1).build();
        Collections.addAll(initMonsters, monster1, monster2, monster3, monster4, monster5);
        return initMonsters;
    }

    /*
    private function
     */

    /**
     * Select a list of monster names with the preset rarity and randomly get a name from the list.
     *
     * @param rarity a integer representing the rarity of the monster
     * @return monster name (String)
     */
    private String getNameRand(int rarity) {
        Random r = new Random();
        List<String> nameList = this.mRarityAndName.get(rarity);
        return nameList.get(r.nextInt(nameList.size()));
    }

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
}