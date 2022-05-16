package main.java.utilities;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    private boolean collision;

    public Tile(int tileSize) {
        this.collision = false;
        // default image
        this.image = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_RGB);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean isCollision() {
        return this.collision;
    }
}
