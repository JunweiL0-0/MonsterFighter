package main.java.model;

public interface MonsterBuilder {
    MonsterBuilder name(String name);
    MonsterBuilder price(int price);
    MonsterBuilder maxHealth(int maxHealth);
    MonsterBuilder damage(int damage);
    MonsterBuilder level(int level);
    MonsterBuilder rarity(int rarity);
    Monster build();
}
