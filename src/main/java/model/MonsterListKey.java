package main.java.model;

public enum MonsterListKey {
    PRICE, MAXHEALTH, DAMAGE, LEVEL;


    public String toString() {
        return switch (this) {
            case PRICE -> "price";
            case MAXHEALTH -> "maxHealth";
            case DAMAGE -> "damage";
            case LEVEL -> "level";
        };
    }
}
