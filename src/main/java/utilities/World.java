package main.java.utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class World {
    private static final String TILE_IMAGE_PATH = "src/main/java/image/tile/";
    private static final String WORLD_FILE_PATH = "src/main/java/utilities/map/world.txt";
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    public static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    public static final int SCALE = 2;
    public static final int UNIT_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 32x32
    public static final int PLAYER_DEFAULT_WORLD_X = (MAX_WORLD_COL/2) * UNIT_SIZE;
    public static final int PLAYER_DEFAULT_WORLD_Y = (MAX_WORLD_ROW/2) * UNIT_SIZE;
    // variable
    private final MyKeyHandler keyHandler;
    private final Player player;
    // double array for storing the map
    private final int[][] encodedWorld;
    private final Tile[] tile;


    public World(Player player, MyKeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        this.player = player;
        this.encodedWorld = new int[MAX_WORLD_ROW][MAX_WORLD_COL];
        this.tile = new Tile[6];
        loadEncodedWorld();
        loadTile();
        setCollisionForTile();
    }

    public void update() {
        movePlayer();
    }

    public void draw(Graphics2D g2) {
        // col and row in the map
        int col = 0;
        int row = 0;
        // draw the map
        while (row < MAX_WORLD_ROW) {
            // top left point's x and y
            int worldX = col * UNIT_SIZE;
            int worldY = row * UNIT_SIZE;
            // put the user in the middle of the world
            int screenX = worldX - this.player.getWorldX() + this.player.getScreenX();
            int screenY = worldY - this.player.getWorldY() + this.player.getScreenY();
            // only paint the map when we need to
            if (paintIsNeeded(worldX, worldY, this.player)) {
                BufferedImage tileImage = this.tile[this.encodedWorld[col][row]].getImage();
                // draw on the panel
                g2.drawImage(tileImage, screenX, screenY, UNIT_SIZE, UNIT_SIZE, null);
            }
            // goes to next col
            col++;
            // goes to next row if we run out of col
            if (col == MAX_WORLD_COL) {
                // reset the col, goes to next row
                col = 0;
                row++;
            }
        }
    }

    public void movePlayer() {
        switch (this.keyHandler.direction()) {
            case 'U' -> this.player.setDirection('U');
            case 'D' -> this.player.setDirection('D');
            case 'L' -> this.player.setDirection('L');
            case 'R' -> this.player.setDirection('R');
        }
        checkCollision(this.player);
        if (!(this.player.isCollisionOn())) {
            switch (this.keyHandler.direction()) {
                case 'U' -> this.player.moveUp();
                case 'D' -> this.player.moveDown();
                case 'L' -> this.player.moveLeft();
                case 'R' -> this.player.moveRight();
            }
            this.player.incrementSpriteCounter();
            if (this.player.getSpriteCounter() >= 20) {
                this.player.toggleSpriteNum();
                this.player.resetSpriteCounter();
            }
        }
    }

    private boolean paintIsNeeded(int worldX, int worldY, Player player) {
        // we add/subtract one tileSize to adjust our screen
        // check it withinScreenWidth
        boolean condition1 = (worldX + UNIT_SIZE) > player.getWorldX() - player.getScreenX();
        boolean condition2 = (worldX - UNIT_SIZE) < player.getWorldX() + player.getScreenX();
        // check it withinScreenHeight
        boolean condition3 = (worldY + UNIT_SIZE) > player.getWorldY() - player.getScreenY();
        boolean condition4 = (worldY - UNIT_SIZE) < player.getWorldY() + player.getScreenY();
        // return true if within the screen
        return condition1 && condition2 && condition3 && condition4;
    }

    private void loadEncodedWorld() {
        try {
            // load the map
            File mapFile = new File(WORLD_FILE_PATH);
            // create a bufferedReader
            BufferedReader br = new BufferedReader(new FileReader(mapFile));
            int col = 0;
            int row = 0;
            // while there is still some cols or rows left in the panel, we put the encoded integer into the 2D array
            while (col < MAX_WORLD_COL && row < MAX_WORLD_ROW) {
                // read one line(row) at a time
                String line = br.readLine();
                // read all col
                while (col < MAX_WORLD_COL) {
                    // split by space
                    String[] numbers = line.split(" ");
                    // convert string into integer
                    int num = Integer.parseInt(numbers[col]);
                    // store it into the double array
                    this.encodedWorld[col][row] = num;
                    // go to next col
                    col++;
                }
                // if col is equals to the maxPanelCol
                if (col == MAX_WORLD_COL) {
                    // reset the col to zero
                    col = 0;
                    // goes to next row
                    row++;
                }
            }
            // close the reader
            br.close();
        } catch (Exception e) {
            System.out.println("Error occurs while loading the Map.");
            e.printStackTrace();
        }
    }

    private void loadTile() {
        try {
            tile[0] = new Tile(UNIT_SIZE);
            tile[0].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "grass.png")));
            tile[1] = new Tile(UNIT_SIZE);
            tile[1].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "earth.png")));
            tile[2] = new Tile(UNIT_SIZE);
            tile[2].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "water.png")));
            tile[3] = new Tile(UNIT_SIZE);
            tile[3].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "sand.png")));
            tile[4] = new Tile(UNIT_SIZE);
            tile[4].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "tree.png")));
            tile[5] = new Tile(UNIT_SIZE);
            tile[5].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "wall.png")));
        } catch (IOException e) {
            System.out.println("Loading image error");
        }
    }

    private void setCollisionForTile() {
        // water, tree, wall
        tile[2].setCollision(true);
        tile[4].setCollision(true);
        tile[5].setCollision(true);
    }

    public void checkCollision(GameEntity entity) {
        entity.setCollisionOn(false);

        int leftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int rightWorldX = leftWorldX + entity.getSolidArea().width;
        int topWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int bottomWorldY = topWorldY + entity.getSolidArea().height;

        int leftCol = leftWorldX/UNIT_SIZE;
        int rightCol = rightWorldX/UNIT_SIZE;
        int topRow = topWorldY/UNIT_SIZE;
        int bottomRow = bottomWorldY/UNIT_SIZE;

        boolean isCollisionCheck1;
        boolean isCollisionCheck2;
        switch (entity.getDirection()) {
            case 'U' -> {
                topRow -= entity.getSize() / UNIT_SIZE;
                isCollisionCheck1 = this.tile[this.encodedWorld[leftCol][topRow]].isCollision();
                isCollisionCheck2 = this.tile[this.encodedWorld[rightCol][topRow]].isCollision();
                if (isCollisionCheck1 || isCollisionCheck2) {
                    entity.setCollisionOn(true);
                }
            }
            case 'D' -> {
                bottomRow += entity.getSize() / UNIT_SIZE;
                isCollisionCheck1 = this.tile[this.encodedWorld[leftCol][bottomRow]].isCollision();
                isCollisionCheck2 = this.tile[this.encodedWorld[rightCol][bottomRow]].isCollision();
                if (isCollisionCheck1 || isCollisionCheck2) {
                    entity.setCollisionOn(true);
                }
            }
            case 'L' -> {
                leftCol -= entity.getSize() / UNIT_SIZE;
                isCollisionCheck1 = this.tile[this.encodedWorld[leftCol][topRow]].isCollision();
                isCollisionCheck2 = this.tile[this.encodedWorld[leftCol][bottomRow]].isCollision();
                if (isCollisionCheck1 || isCollisionCheck2) {
                    entity.setCollisionOn(true);
                }
            }
            case 'R' -> {
                rightCol += entity.getSize() / UNIT_SIZE;
                isCollisionCheck1 = this.tile[this.encodedWorld[rightCol][topRow]].isCollision();
                isCollisionCheck2 = this.tile[this.encodedWorld[rightCol][bottomRow]].isCollision();
                if (isCollisionCheck1 || isCollisionCheck2) {
                    entity.setCollisionOn(true);
                }
            }
        }
    }
}
