package main.java.model;

/**
 * MonsterBuilderImpl handle the creation of the Monster through exposed methods by implementing the MonsterBuilder interface
 */
public class MonsterBuilderImpl implements MonsterBuilder {
    private String name;
    private int price;
    private int maxHealth;
    private int damage;
    private int level;
    private int rarity;


    /**
     * Set the name for the monster.
     *
     * @param name a string represents the name of the monster
     * @return instance itself
     */
    public MonsterBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the price for the monster.
     *
     * @param price an integer represents the price of the monster
     * @return instance itself
     */
    public MonsterBuilder price(int price) {
        this.price = price;
        return this;
    }

    /**
     * Set the maxHealth for the monster.
     *
     * @param maxHealth an integer represents the maxHealth of the monster
     * @return instance itself
     */
    public MonsterBuilder maxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    /**
     * Set the damage for the monster.
     *
     * @param damage an integer represents the damage of the monster
     * @return instance itself
     */
    public MonsterBuilder damage(int damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Set the level for the monster.
     *
     * @param level an integer represents the level of the monster
     * @return instance itself
     */
    public MonsterBuilder level(int level) {
        this.level = level;
        return this;
    }

    /**
     * Set the rarity for the monster.
     *
     * @param rarity an integer represents the rarity of the monster
     * @return instance itself
     */
    public MonsterBuilder rarity(int rarity) {
        this.rarity = rarity;
        return this;
    }

    /**
     * Build and return a monster instance.
     *
     * @return a newly created monster instance.
     */
    public Monster build() {
        return new Monster(this.name, this.price, this.maxHealth, this.damage, this.level, this.rarity);
    }
}
