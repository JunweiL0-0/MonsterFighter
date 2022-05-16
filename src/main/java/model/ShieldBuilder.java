package main.java.model;

import javax.swing.ImageIcon;

public interface ShieldBuilder {
	ShieldBuilder name(String name);
    ShieldBuilder price(int price);
    ShieldBuilder shield(int shield);
    ShieldBuilder rarity(int rarity);
    ShieldBuilder rarityStr(String rarityStr);
    ShieldBuilder imageIcon(ImageIcon imageIcon);
    Shield build();
}
