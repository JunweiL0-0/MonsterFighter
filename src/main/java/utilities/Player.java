package main.java.utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends GameEntity {
    private static final String PLAYER_IMAGE_PATH = "src/main/java/image/player/";
    public static final int ORIGINAL_UNIT_SIZE = 16; // 16x16
    public static final int SCALE = 3;
    public static final int PLAYER_SIZE = ORIGINAL_UNIT_SIZE * SCALE; // 32x32
    public static final int PLAYER_SPEED = 4;


    public Player() {
        super(World.PLAYER_DEFAULT_WORLD_X, World.PLAYER_DEFAULT_WORLD_Y,  PLAYER_SPEED, PLAYER_SIZE);
        setScreenX(GamePanel.PLAYER_SCREEN_X);
        setScreenY(GamePanel.PLAYER_SCREEN_Y);
        getPlayerImage();
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
