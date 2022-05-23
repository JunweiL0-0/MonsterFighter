package main.java.utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Enemy extends GameEntity {
    private static final String ENEMY_IMAGE_PATH = "src/main/java/image/enemy/";
    private final GameEntity player;

    public Enemy(int worldX, int worldY, int speed, int size, GameEntity player) {
        super(worldX, worldY, speed, size);
        this.player = player;
        getEnemyImage();
    }

    public void getEnemyImage() {
//        try {
        setImageUp1(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageUp2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageDown1(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageDown2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageLeft1(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageLeft2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageRight1(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        setImageRight2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
    }

    public void draw(Graphics2D g2) {
        int screenX = getWorldX() - this.player.getWorldX() + this.player.getScreenX();
        int screenY = getWorldY() - this.player.getWorldY() + this.player.getScreenY();
        setScreenX(screenX);
        setScreenY(screenY);
        super.draw(g2);
    }
}
