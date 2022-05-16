package main.java.utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Enemy extends GameEntity {
    private static final String ENEMY_IMAGE_PATH = "src/main/java/image/enemy/";
    public static final int ORIGINAL_UNIT_SIZE = 16; // 16x16
    public static final int SCALE = 3;
    public static final int ENEMY_SIZE = ORIGINAL_UNIT_SIZE * SCALE; // 32x32
    public static final int ENEMY_SPEED = 4;

    private final int monsterTeamIndex;
    private final Player player;

    public Enemy(int worldX, int worldY, int speed, int size, int monsterTeamIndex, Player player) {
        super(worldX, worldY, speed, size);
        this.monsterTeamIndex = monsterTeamIndex;
        this.player = player;
        getEnemyImage();
    }

    public void getEnemyImage() {
        try {
            setImageUp1(ImageIO.read(new File(ENEMY_IMAGE_PATH + "up1.png")));
            setImageUp2(ImageIO.read(new File(ENEMY_IMAGE_PATH + "up2.png")));
            setImageDown1(ImageIO.read(new File(ENEMY_IMAGE_PATH + "down1.png")));
            setImageDown2(ImageIO.read(new File(ENEMY_IMAGE_PATH + "down2.png")));
            setImageLeft1(ImageIO.read(new File(ENEMY_IMAGE_PATH + "left1.png")));
            setImageLeft2(ImageIO.read(new File(ENEMY_IMAGE_PATH + "left2.png")));
            setImageRight1(ImageIO.read(new File(ENEMY_IMAGE_PATH + "right1.png")));
            setImageRight2(ImageIO.read(new File(ENEMY_IMAGE_PATH + "right2.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = getWorldX() - this.player.getWorldX() + this.player.getScreenX();
        int screenY = getWorldY() - this.player.getWorldY() + this.player.getScreenY();
        setScreenX(screenX);
        setScreenY(screenY);
        super.draw(g2);
    }
}
