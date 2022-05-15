package main.java.utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends GameEntity {
    private static final String PLAYER_IMAGE_PATH = "src/main/java/image/player/";
    public static final int ORIGINAL_UNIT_SIZE = 16; // 16x16 tile
    public static final int SCALE = 2;
    public static final int PLAYER_SIZE = ORIGINAL_UNIT_SIZE * SCALE; // 16x16 tile
    public static final int PLAYER_SPEED = 4;

    private final int screenX;
    private final int screenY;


    public Player() {
        super(World.PLAYER_DEFAULT_WORLD_X, World.PLAYER_DEFAULT_WORLD_Y, PLAYER_SPEED, World.UNIT_SIZE);
        this.screenX = GamePanel.PLAYER_SCREEN_X;
        this.screenY = GamePanel.PLAYER_SCREEN_Y;
        getPlayerImage();
    }

    public int getScreenX() {
        return this.screenX;
    }

    public int getScreenY() {
        return this.screenY;
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

    public void getPlayerImage() {
        try {
            setImageUp1(ImageIO.read(new File(PLAYER_IMAGE_PATH + "up1.png")));
            setImageUp2(ImageIO.read(new File(PLAYER_IMAGE_PATH + "up2.png")));
            setImageDown1(ImageIO.read(new File(PLAYER_IMAGE_PATH + "down1.png")));
            setImageDown2(ImageIO.read(new File(PLAYER_IMAGE_PATH + "down2.png")));
            setImageLeft1(ImageIO.read(new File(PLAYER_IMAGE_PATH + "left1.png")));
            setImageLeft2(ImageIO.read(new File(PLAYER_IMAGE_PATH + "left2.png")));
            setImageRight1(ImageIO.read(new File(PLAYER_IMAGE_PATH + "right1.png")));
            setImageRight2(ImageIO.read(new File(PLAYER_IMAGE_PATH + "right2.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
