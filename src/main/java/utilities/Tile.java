package main.java.utilities;

import java.awt.image.BufferedImage;

/**
 * This class is designed for gamePanel for represent the tile on the screen.
 */
public class Tile {
    private BufferedImage image;
    private boolean collision;


    /**
     * The constructor of the tile.
     *
     * @param tileSize an integer represent the size of the tile
     */
    public Tile(int tileSize) {
        this.collision = false;
        // default image
        this.image = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Set the image of the tile
     *
     * @param image a bufferedImage of the tile
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Return the image of the tile
     *
     * @return the image of the tile
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Set the collision to true if there is a collision happens
     *
     * @param collision boolean value
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    /**
     * Return true if there is a collision happens. Otherwise, return false.
     *
     * @return a boolean value
     */
    public boolean isCollision() {
        return this.collision;
    }
}
