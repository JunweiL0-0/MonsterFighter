package main.java.model;

/**
 * A class representing monster.
 */
public class Monster extends GameItem {
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private int level;


    /**
     * GameItem's constructor.
     *
     * @see GameItem#GameItem(String, int)
     * @param name a string representing the monster's name
     * @param price an integer representing the monster's price
     * @param maxHealth an integer representing the monster's maxHealth
     * @param damage an integer representing the monster's damage
     * @param level an integer representing the monster's level
     */
    public Monster(String name, int price, int maxHealth, int damage, int level) {
        super(name, price);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.damage = damage;
        this.level = level;
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
     * A getter function for the monster's property "rarity".
     * This function will calculate and return current monster's rarity.
     *
     * @return an integer which representing current monster's rarity.
     */
    public int getRarity() {
        if (this.damage > 200) {
            return 5;
        } else {
            return 1;
        }
    }

}
