package main.java.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import main.java.utilities.RandomPicker;


/**
 * The class is used for (randomly) generating gameItem base on the data provided by ItemDataStorage.
 *
 * @see ItemDataStorage
 */
public class Generator {
    private ItemDataStorage itemDataStorage;
    private RandomPicker randomPicker;


    /**
     * Constructor for Generator
     *
     * @param itemDataStorage ItemDataStorage instance
     * @param randomPicker RandomPicker instance
     * @see ItemDataStorage
     * @see RandomPicker
     */
    public Generator(ItemDataStorage itemDataStorage, RandomPicker randomPicker) {
        this.itemDataStorage = itemDataStorage;
        this.randomPicker = randomPicker;
    }

    /**
     * Randomly generate a monster.
     * This function will construct a monster by using the data stored in the ItemDataStorage.
     *
     * @return a single randomly generated monster.
     */
    public Monster generateMonster() {
        // randomly pick a monster from itemDataStorage
        List<String> monsterNames = new ArrayList<>(this.itemDataStorage.getMonsters().keySet());
        String monsterName = this.randomPicker.getItemFromStrList(monsterNames);
        // price, health, damage, level
        Map<MonsterListKey, List<Integer>> monsterInfo = this.itemDataStorage.getMonsters().get(monsterName);
        int price = this.randomPicker.getItemFromIntList(monsterInfo.get(MonsterListKey.PRICE));
        int maxHealth = this.randomPicker.getItemFromIntList(monsterInfo.get(MonsterListKey.MAXHEALTH));
        int damage = this.randomPicker.getItemFromIntList(monsterInfo.get(MonsterListKey.DAMAGE));
        int level = this.randomPicker.getItemFromIntList(monsterInfo.get(MonsterListKey.LEVEL));
        // generate and return a new monster
        return new Monster(monsterName, price, maxHealth, damage, level);
    }

    /**
     * This function is aim to generate the initMonsters for the very first round of the game.
     *
     * @return a list of initMonster
     */
    public ArrayList<Monster> getInitialMonsters() {
        ArrayList<Monster> initMonsters = new ArrayList<>();
        Monster monster1 = new Monster("Monster1", 100, 500, 200, 1);
        Monster monster2 = new Monster("Monster2", 100, 550, 150, 1);
        Monster monster3 = new Monster("Monster3", 100, 600, 100, 1);
        Monster monster4 = new Monster("Monster4", 100, 700, 50, 1);
        Monster monster5 = new Monster("Monster5", 100, 800, 30, 1);
        Collections.addAll(initMonsters, monster1, monster2, monster3, monster4, monster5);
        return initMonsters;
    }
}