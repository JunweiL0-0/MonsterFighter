package main.java.model;

/**
 * This is an interface which exposes all methods required for an implementation of a builder to create the monster class
 */
public interface MonsterBuilder {
    MonsterBuilder name(String name);
    MonsterBuilder price(int price);
    MonsterBuilder maxHealth(int maxHealth);
    MonsterBuilder damage(int damage);
    MonsterBuilder level(int level);
    MonsterBuilder rarity(int rarity);
    Monster build();
}
