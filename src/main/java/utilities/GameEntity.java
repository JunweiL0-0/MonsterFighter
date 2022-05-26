package main.java.utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The entity on the gamePanel.
 */
public class GameEntity {
    private final int speed;
    private final int size;
    private int worldX;
    private int worldY;
    private int screenX;
    private int screenY;
    private int actionCounter;
    // direction
    private char direction; // Up:'U' Down:'D' Left:'L' Right:'R'
    private int spriteNum; // used to toggle the image to make the entity looks like running
    private int spriteCounter; // changing the speed of toggling image. This will change how fast the player running. Note: Visually
    // toggle these images to visualize the running of the user.
    private BufferedImage up1; // up1 image
    private BufferedImage up2; // up2 image
    private BufferedImage down1; // down1 image
    private BufferedImage down2; // down2 image
    private BufferedImage left1; // left1 image
    private BufferedImage left2; // left2 image
    private BufferedImage right1; // right1 image
    private BufferedImage right2; // right2 image
    private Rectangle solidArea;
    private static final int SOLID_AREA_DEFAULT_X = 8;
    private static final int SOLID_AREA_DEFAULT_Y = 18;
    private static final int SOLID_AREA_DEFAULT_WIDTH = 32;
    private static final int SOLID_AREA_DEFAULT_HEIGHT = 32;
    private boolean collisionOn;


    /**
     * Constructor of the entity.
     *
     * @param worldX x-axis in the world
     * @param worldY y-axis in the world
     * @param speed moving speed of the entity
     * @param size the size of the entity displaying on the screen
     */
    public GameEntity(int worldX, int worldY, int speed, int size) {
        this.worldX = worldX;
        this.worldY = worldY;
        // default x and y
        this.screenX = 100;
        this.screenY = 100;
        this.speed = speed;
        this.size = size;
        this.direction = 'D';
        this.spriteNum = 1;
        this.spriteCounter = 0;
        this.solidArea = new Rectangle(SOLID_AREA_DEFAULT_X, SOLID_AREA_DEFAULT_Y, SOLID_AREA_DEFAULT_WIDTH, SOLID_AREA_DEFAULT_HEIGHT);
        this.collisionOn = false;
        this.actionCounter = 0;
    }

    /**
     * Reset the solid area to default
     */
    public void resetSolidArea() {
        this.solidArea.x = SOLID_AREA_DEFAULT_X;
        this.solidArea.y = SOLID_AREA_DEFAULT_Y;
        this.solidArea.width = SOLID_AREA_DEFAULT_WIDTH;
        this.solidArea.height = SOLID_AREA_DEFAULT_HEIGHT;
    }

    /**
     * Return the entity's y-axis in the world
     *
     * @return an integer represent the y-axis of the entity
     */
    public int getWorldY() {
        return this.worldY;
    }

    /**
     * Return the entity's x-axis in the world
     *
     * @return an integer represent the x-axis of the entity
     */
    public int getWorldX() {
        return this.worldX;
    }

    /**
     * Return the entity's speed
     *
     * @return an integer represent the speed of the entity
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Return the size of the entity
     *
     * @return an integer represent the entity's size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Return the direction of the entity
     *
     * @return a char represent the entity's direction
     */
    public char getDirection() {
        return this.direction;
    }

    /**
     * Set the image of the entity.
     *
     * @param image an image which will be used to animate the movement of the entity
     */
    public void setImageUp1(BufferedImage image) {
        this.up1 = image;
    }

    /**
     * Set the image of the entity.
     *
     * @param image an image which will be used to animate the movement of the entity
     */
    public void setImageUp2(BufferedImage image) {
        this.up2 = image;
    }

    /**
     * Set the image of the entity.
     *
     * @param image an image which will be used to animate the movement of the entity
     */
    public void setImageDown1(BufferedImage image) {
        this.down1 = image;
    }

    /**
     * Set the image of the entity.
     *
     * @param image an image which will be used to animate the movement of the entity
     */
    public void setImageDown2(BufferedImage image) {
        this.down2 = image;
    }

    /**
     * Set the image of the entity.
     *
     * @param image an image which will be used to animate the movement of the entity
     */
    public void setImageLeft1(BufferedImage image) {
        this.left1= image;
    }

    /**
     * Set the image of the entity.
     *
     * @param image an image which will be used to animate the movement of the entity
     */
    public void setImageLeft2(BufferedImage image) {
        this.left2 = image;
    }

    /**
     * Set the image of the entity.
     *
     * @param image an image which will be used to animate the movement of the entity
     */
    public void setImageRight1(BufferedImage image) {
        this.right1 = image;
    }

    /**
     * Set the image of the entity.
     *
     * @param image an image which will be used to animate the movement of the entity
     */
    public void setImageRight2(BufferedImage image) {
        this.right2 = image;
    }

    /**
     * Set the collision to true if the collision is being detected. Otherwise, false.
     *
     * @param val a boolean represent whether there is a collision happens.
     */
    public void setCollisionOn(boolean val) {
        this.collisionOn = val;
    }

    /**
     * Set the direction of the entity.
     *
     * @param direction a char value represent the direction of the entity
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public BufferedImage getImageUp1() {
        return this.up1;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public BufferedImage getImageUp2() {
        return this.up2;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public BufferedImage getImageDown1() {
        return this.down1;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public BufferedImage getImageDown2() {
        return this.down2;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public BufferedImage getImageLeft1() {
        return this.left1;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public BufferedImage getImageLeft2() {
        return this.left2;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public BufferedImage getImageRight1() {
        return this.right1;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public BufferedImage getImageRight2() {
        return this.right2;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public Rectangle getSolidArea() {
        return this.solidArea;
    }

    /**
     * Return the image of the entity. The image will be used to animate the movement of the entity.
     *
     * @return an image
     */
    public int getSpriteNum() {
        return spriteNum;
    }

    public int getSpriteCounter() {
        return this.spriteCounter;
    }

    public void incrementSpriteCounter() {
        this.spriteCounter++;
        if (this.spriteCounter >= 20) {
            toggleSpriteNum();
            resetSpriteCounter();
        }
    }
    public void resetSpriteCounter() {
        this.spriteCounter = 0;
    }
    public void toggleSpriteNum() {
        this.spriteNum = (this.spriteNum + 1) % 2;
    }

    public boolean isCollisionOn() {
        return this.collisionOn;
    }

    public void moveUp() {
        this.worldY -= this.speed;
    }

    public void moveDown() {
        this.worldY += this.speed;
    }

    public void moveLeft() {
        this.worldX -= this.speed;
    }

    public void moveRight() {
        this.worldX += this.speed;
    }

    public int getScreenX() {
        return this.screenX;
    }

    public int getScreenY() {
        return this.screenY;
    }

    public void setScreenX(int val) {
        this.screenX = val;
    }

    public void setScreenY(int val) {
        this.screenY = val;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (getDirection()) {
            case 'U' -> {
                if (getSpriteNum() == 1) {
                    image = getImageUp1();
                } else {
                    image = getImageUp2();
                }
            }
            case 'D' -> {
                if (getSpriteNum() == 1) {
                    image = getImageDown1();
                } else {
                    image = getImageDown2();
                }
            }
            case 'L' -> {
                if (getSpriteNum() == 1) {
                    image = getImageLeft1();
                } else {
                    image = getImageLeft2();
                }
            }
            case 'R' -> {
                if (getSpriteNum() == 1) {
                    image = getImageRight1();
                } else {
                    image = getImageRight2();
                }
            }
        }
        // stay in the middle of the screen
        g2.drawImage(image, this.screenX, this.screenY, getSize(), getSize(), null);
    }

    public void incrementActionCounter() {
        this.actionCounter++;
    }
    public int getActionCounter() {
        return this.actionCounter;
    }
    public void resetActionCounter() {
        this.actionCounter = 0;
    }
}
