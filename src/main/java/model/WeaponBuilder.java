package main.java.model;

import javax.swing.ImageIcon;

public interface WeaponBuilder {
	WeaponBuilder name(String name);
    WeaponBuilder price(int price);
    WeaponBuilder damage(int damage);
    WeaponBuilder rarity(int rarity);
    WeaponBuilder rarityStr(String rarityStr);
    WeaponBuilder imageIcon(ImageIcon imageIcon);
    Weapon build();
}
