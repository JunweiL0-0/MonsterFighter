package main.java.controller;

import main.java.model.DefaultMonsterName;
import main.java.model.Generator;
import main.java.model.ItemDataStorage;
import main.java.model.Shop;

/**
 * A java.controller handling the game logic.
 */
public class GameController {
    private ItemDataStorage itemDataStorage;
    private Generator generator;
    private Shop shop;


    public GameController() {
        this.itemDataStorage = new ItemDataStorage();
        this.generator = new Generator(this.itemDataStorage);
        this.shop = new Shop(this.generator);
    }


    /**
     * A function used to start the game.
     */
    public void startGame() {

//        System.out.println(new MonsterGenerator().generateMonster());

        System.out.println(new ItemDataStorage().getMonsters().get(DefaultMonsterName.MONSTER1).get("price"));
    }

}
