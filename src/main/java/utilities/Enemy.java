package main.java.utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This is the class used to create the enemy on the screen.
 * It will randomly walk on the screen and will check collision.
 */
public class Enemy extends GameEntity {
    // We need to know the position of the player
    private final GameEntity player;


    /**
     * The constructor of the enemy.
     *
     * @param worldX the x-axis of the enemy in the world
     * @param worldY the y-axis of the enemy in the world
     * @param speed the speed of the enemy
     * @param size the size of the enemy
     * @param player the player
     */
    public Enemy(int worldX, int worldY, int speed, int size, GameEntity player) {
        super(worldX, worldY, speed, size);
        this.player = player;
        getEnemyImage();
    }

    /**
     * Create and set the image of the enemy.
     */
    public void getEnemyImage() {
        setImageUp1(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageUp2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageDown1(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageDown2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageLeft1(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageLeft2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageRight1(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageRight2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
    }

    /**
     * This method can be seen as using a pencil to draw the enemy.
     *
     * @param g2 the pencil
     */
    public void draw(Graphics2D g2) {
        int screenX = getWorldX() - this.player.getWorldX() + this.player.getScreenX();
        int screenY = getWorldY() - this.player.getWorldY() + this.player.getScreenY();
        setScreenX(screenX);
        setScreenY(screenY);
        super.draw(g2);
    }
}
