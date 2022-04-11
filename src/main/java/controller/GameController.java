package main.java.controller;

import main.java.model.Generator;
import main.java.model.ItemDataStorage;
import main.java.model.Shop;
import main.java.utilities.ListGenerator;
import main.java.utilities.RandomPicker;

/**
 * A java.controller handling the game logic.
 */
public class GameController {
    private ItemDataStorage itemDataStorage;
    private ListGenerator listGenerator;
    private Generator generator;
    private Shop shop;
    private RandomPicker randomPicker;


    /**
     * Constructor for the GameController.
     */
    public GameController() {
        this.randomPicker = new RandomPicker();
        this.listGenerator = new ListGenerator();
        this.itemDataStorage = new ItemDataStorage(this.listGenerator);
        this.generator = new Generator(this.itemDataStorage, this.randomPicker);
        this.shop = new Shop(this.generator);
    }


    /**
     * A function used to start the game.
     */
    public void startGame() {

//        System.out.println(new MonsterGenerator().generateMonster());
//        System.out.println(this.generator.generateMonster());
        System.out.println(this.shop.getItemForSell());
        System.out.println(this.shop.purchaseItem(1));
    }

}
