package main.java.model;

public enum DefaultMonsterName {
    MONSTER1, MONSTER2;


    public String toString() {
        return switch (this) {
            case MONSTER1 -> "Monster1";
            case MONSTER2 -> "Monster2";
        };
    }
}
