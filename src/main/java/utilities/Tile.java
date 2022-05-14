package main.java.utilities;

import java.awt.image.BufferedImage;

public class Tile {
    private static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    private static final int SCALE = 2;
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 32x32
    private BufferedImage image;
    private boolean collision;

    public Tile() {
        this.collision = false;
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

    public int getSize() {
        return TILE_SIZE;
    }

    public boolean isCollision() {
        return this.collision;
    }
}
