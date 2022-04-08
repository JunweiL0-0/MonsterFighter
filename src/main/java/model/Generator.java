package main.java.model;

import main.java.utilities.RandomPicker;

import javax.swing.text.Utilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * Randomly generate a monster
     *
     * @return a randomly generated monster
     */
    public Monster generateMonster() {
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
}