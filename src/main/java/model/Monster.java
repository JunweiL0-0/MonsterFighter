package main.java.model;

import javax.swing.*;

/**
 * A class representing monster.
 */
public class Monster extends GameItem {
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private int level;
    private int maxExp;
    private int exp;
    private int actionCounter;
    private ImageIcon imageIcon;


    /**
     * GameItem's constructor.
     *
     * @see GameItem#GameItem(String, int, int)
     * @param name a string representing the monster's name
     * @param price an integer representing the monster's price
     * @param maxHealth an integer representing the monster's maxHealth
     * @param damage an integer representing the monster's damage
     * @param level an integer representing the monster's level
     * @param rarity an integer representing the monster's rarity
     */
    public Monster(String name, int price, int maxHealth, int damage, int level, int rarity, ImageIcon icon) {
        super(name, price, rarity);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.damage = damage;
        this.level = level;
        this.imageIcon = icon;
        this.actionCounter = 0;
        this.maxExp = 100;
        this.exp = 0;
    }

    /**
     * Return true if the monster's currentHealth is lower or equal to zero. False otherwise.
     * The monster's health shouldn't be lower than zero, but we put here anyway...
     *
     * @return a boolean value
     */
    public boolean isFainted() {
        return this.currentHealth <= 0;
    }

    /**
     * Override the toString() method. This function will return the description of the current monster.
     *
     * @return a string contains current monster's information.
     */
    public String toString() {
        String monsterName = String.format("Name: %s\n", this.getName());
        String monsterPrice = String.format("Price: %s\n", this.getPrice());
        String monsterRefundPrice = String.format("RefundPrice: %s\n", this.getRefundPrice());
        String monsterRarity = String.format("Rarity: %s\n", this.getRarity());
        String monsterMaxHealth = String.format("MaxHealth: %s\n", this.getMaxHealth());
        String monsterCurrentHealth = String.format("CurrentHealth: %s\n", this.getCurrentHealth());
        String monsterDamage = String.format("Damage: %s\n", this.getDamage());
        String monsterLevel = String.format("Level: %s\n", this.getLevel());
        return monsterName + monsterPrice + monsterRefundPrice + monsterRarity +
                monsterMaxHealth + monsterCurrentHealth + monsterDamage + monsterLevel;
    }

    /* getters go here */
    /**
     * Return the maxHealth for current monster.
     *
     * @return an integer which representing current monster's maxHealth.
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Return the currentHealth for current monster.
     *
     * @return an integer which representing current monster's currentHealth.
     */
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    /**
     * Return the damage for current monster.
     *
     * @return an integer which representing current monster's damage.
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Return the level for current monster.
     *
     * @return an integer which representing current monster's level.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * This function will return a imageIcon the monster's has.
     * The imageIcon will be given to a monster when the monster was being constructed.
     *
     * @return the imageIcon of the monster.
     */
    public ImageIcon getImageIcon() {
        return this.imageIcon;
    }

    /**
     * This method is used to get the actionCounter of the monster. Every time when the monster attack, the actionCounter
     * will be increased one.
     *
     * @return the actionCounter of the monster
     */
    public int getActionCounter() {
        return this.actionCounter;
    }

    /**
     * This method will reset the actionCounter of this monster to zero.
     */
    public void resetActionCounter() {
        this.actionCounter = 0;
    }

    /**
     * Receive an incomingDamage and decrease the currentHealth of the monster.
     *
     * @param incomingDamage an integer represent the incomingDamage created by other monsters.
     */
    public void harmBy(int incomingDamage) {
        this.currentHealth = Math.max(0, this.currentHealth - incomingDamage);
    }

    /**
     * Increase the actionCounter by one.
     */
    public void incrementActionCounter() {
        this.actionCounter += 1;
    }

    /**
     * Take a medicine and apply the effect to the monster.
     *
     * @param med a medicine instance which will result some effect on the monster.
     */
    public void healBy(Medicine med) {
    	this.currentHealth = Math.min(this.maxHealth, this.currentHealth + med.getEffect());
    }

    public void increaseMaxHealth(int health) {
        this.maxHealth += health;
    }

    /**
     * Increase the Exp.
     *
     * @param exp an integer represents the amount of exp which will be added to the monster.
     */
    public void incrementExpBy(int exp) {
        this.exp += exp;
        if (this.exp == 100) {
            this.exp = 0;
            levelUp();
        }
    }
    
    public int getExp() {
    	return this.exp;
    }
    
    public int getMaxExp() {
    	return this.maxExp;
    }

    /**
     * Levelup the monster and increase damage/maxHealth/currentHealth of the monster.
     */
    public void levelUp() {
        this.level++;
        this.damage += 50;
        this.maxHealth += 100;
        this.currentHealth += 100;
    }

    public void increaseDamage(int damage) {
        this.damage += damage;
    }

    public void recover() {
        this.currentHealth = this.maxHealth;
    }
}
