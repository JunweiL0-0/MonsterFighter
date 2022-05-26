package main.java.utilities;

import main.java.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


/**
 * This is the gamePanel which is designed for animate enemies and player's movement on the screen.
 * This gamePanel will result another thread and the panel will be repaint on 80 Hz frequency.
 * The height and weight of this panel is being fixed but you can change the location of the panel. (X-axis, Y-axis)
 * Functions which are being driven by thread should be set to public.
 * PanelFeature: Notify the gameController when player encounter enemy. Check collision. Custom the amount of enemies on the screen.
 */
public class GamePanel extends JPanel implements Runnable {
    // file paths
    private static final String WORLD_FILE_PATH = "world.txt";
    // constant value
    private static final int MAX_WORLD_COL = 50;
    private static final int MAX_WORLD_ROW = 50;
    public static final int ORIGINAL_UNIT_SIZE = 16; // 16x16
    public static final int SCALE = 3;
    public static final int PLAYER_SIZE = ORIGINAL_UNIT_SIZE * SCALE; // 48
    public static final int UNIT_SIZE = ORIGINAL_UNIT_SIZE * SCALE; // 48
    public static final int PLAYER_SPEED = 4;
    public static final int SCREEN_WIDTH = 560;
    public static final int SCREEN_HEIGHT = 280;
    public static final int PLAYER_SCREEN_X = ((SCREEN_WIDTH/2) - (PLAYER_SIZE/2));
    public static final int PLAYER_SCREEN_Y = ((SCREEN_HEIGHT/2) - (PLAYER_SIZE/2));
    public static final int PLAYER_DEFAULT_WORLD_X = (MAX_WORLD_COL/2) * UNIT_SIZE;
    public static final int PLAYER_DEFAULT_WORLD_Y = (MAX_WORLD_ROW/2) * UNIT_SIZE;
    public static final double FPS = 80.0; // FPS
    public static final double DRAW_INTERVAL = 1000000000/FPS; // calculate the 80 fps time
    // variable
    private final MyKeyHandler myKeyHandler;
    private final GameController gc;
    private final int[][] encodedWorld;
    private final Tile[] tiles;
    private final Thread gameThread;
    private GameEntity player;
    private ArrayList<GameEntity> enemies;
    private boolean encounterEnemy;
    private int encounteredEnemyIndex;
    private int currentTotalBattle;


    /**
     * The constructor of the gamePanel
     *
     * @param x the x-axis of the gamePanel
     * @param y the y-axis of the gamePanel
     * @param gc the gameController
     */
    public GamePanel(int x, int y, GameController gc) {
        this.currentTotalBattle = 0;
        this.myKeyHandler = new MyKeyHandler();
        this.gc = gc;
        this.encodedWorld = new int[MAX_WORLD_ROW][MAX_WORLD_COL];
        this.tiles = new Tile[6];
        // init the panel
        placePanel(x, y);
        initPlayer();
        initEnemies();
        initWorld();
        // listen key pressing event
        this.addKeyListener(this.myKeyHandler);
        // Threading
        this.gameThread = new Thread(this); // create a new thread
        this.gameThread.start(); // this will auto call the run function in this class
    }

    /**
     * Update the gamePanel. Move Enemies/Player and check if a collision happened between the player and the enemy.
     * If a player encounter an enemy, the gamePanel will store the index of the enemy back to the gameController.
     *
     * @see GameController::storeBattleIncex
     */
    public void updatePanel() {
        if (this.gc.getTotalBattle() != this.currentTotalBattle) {
            initEnemies();
        }
        // update world
        if (this.encounterEnemy) {
            this.gc.storeBattleIndex(this.encounteredEnemyIndex);
            this.encounterEnemy = false;
        }
        movePlayer();
        moveEnemy();
    }

    /**
     * Pint the component on the screen so that the player can interact with our system
     *
     * @param g you can think this like a pencil
     */
    public void paintComponent(Graphics g) {
        // this will be called by the rePaint()
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast it
        // tile should be displayed before player
        drawWorld(g2); // draw the map
        drawPlayer(g2); // draw the player
        for (GameEntity enemy : this.enemies) {
            enemy.draw(g2);
        }
        g2.dispose(); // dispose of this g2 and release system resources
    }

    /**
     * Place the player into the screen and init his position and load image.
     */
    private void initPlayer() {
        this.player = new GameEntity(PLAYER_DEFAULT_WORLD_X, PLAYER_DEFAULT_WORLD_Y,  PLAYER_SPEED, PLAYER_SIZE);
        this.player.setScreenX(GamePanel.PLAYER_SCREEN_X);
        this.player.setScreenY(GamePanel.PLAYER_SCREEN_Y);
        getPlayerImage();
    }

    /**
     * Load the world.txt and put the encodedWorld into a list.
     */
    private void loadEncodedWorld() {
        try {
            // load the map
            // create a bufferedReader
        	InputStream in = getClass().getResourceAsStream(WORLD_FILE_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
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

    /**
     * Assign colors to tiles and add them to the tiles list
     */
    private void loadTile() {
    	// create an empty image
        BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        // create a "pencil" to draw on the image
    	Graphics2D g2 = image.createGraphics();
    	// set and fill the image with rgb color value
    	g2.setColor(new Color(85, 107, 47));
    	g2.fillRect(0, 0, UNIT_SIZE*4, UNIT_SIZE*4);
    	// color for tile 0
        tiles[0] = new Tile(UNIT_SIZE);
        tiles[0].setImage(image);
        // color for tile 1
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    	g2 = image.createGraphics();
        g2.setColor(new Color(0, 107, 47));
    	g2.fillRect(0, 0, UNIT_SIZE*4, UNIT_SIZE*4);
        tiles[1] = new Tile(UNIT_SIZE);
        tiles[1].setImage(image);
        // color for tile 2
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    	g2 = image.createGraphics();
        g2.setColor(new Color(153, 76, 0));
    	g2.fillRect(0, 0, UNIT_SIZE*4, UNIT_SIZE*4);
        tiles[2] = new Tile(UNIT_SIZE);
        tiles[2].setImage(image);
        // color for tile 3
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    	g2 = image.createGraphics();
        tiles[3] = new Tile(UNIT_SIZE);
        tiles[3].setImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        // color for tile 4
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    	g2 = image.createGraphics();
        g2.setColor(new Color(51, 153, 255));
    	g2.fillRect(0, 0, UNIT_SIZE*4, UNIT_SIZE*4);
        tiles[4] = new Tile(UNIT_SIZE);
        tiles[4].setImage(image);
        // color for tile 5
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    	g2 = image.createGraphics();
        g2.setColor(new Color(255, 153,255));
    	g2.fillRect(0, 0, UNIT_SIZE*4, UNIT_SIZE*4);
        tiles[5] = new Tile(UNIT_SIZE);
        tiles[5].setImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
    }

    /**
     * If a tile is a solid type(the player/enemy can not go through, we set the tile's collision to true.
     */
    private void setCollisionForTile() {
        // water, tree, wall
        tiles[2].setCollision(true);
        tiles[4].setCollision(true);
        tiles[5].setCollision(true);
    }

    /**
     * Load world/tileImage/ and set the collision for tiles.
     */
    private void initWorld() {
        // load world from file
        loadEncodedWorld();
        // load tile image from file
        loadTile();
        // set solid tile
        setCollisionForTile();
    }

    /**
     * Generate the enemies and place them into the world.
     */
    public void initEnemies() {
        this.enemies = new ArrayList<>();
        this.currentTotalBattle = this.gc.getTotalBattle();
        for (int i=0; i<this.gc.getTotalBattle(); i++) {
            this.enemies.add(new Enemy(PLAYER_DEFAULT_WORLD_X-(150*(i+1)), PLAYER_DEFAULT_WORLD_Y - 2, 1, UNIT_SIZE, this.player));
        }
    }

    /**
     * Get and set the player's images. We can use these images to animate the running of player.
     */
    public void getPlayerImage() {
    	// create a white image
        BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    	Graphics2D g2 = image.createGraphics();
    	g2.setColor(new Color(255, 255, 255));
    	g2.fillRect(0, 0, PLAYER_SIZE*5, PLAYER_SIZE*5);
    	// set player's images with black and white images and this will result a blinking object on the screen)
        this.player.setImageUp1(image);
        this.player.setImageUp2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        this.player.setImageDown1(image);
        this.player.setImageDown2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        this.player.setImageLeft1(image);
        this.player.setImageLeft2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
        this.player.setImageRight1(image);
        this.player.setImageRight2(new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
    }

    /**
     * Place the panel to a position. The width and height of the gamePanel are fixed.
     *
     * @param x the x-axis
     * @param y the y-axis
     */
    private void placePanel(int x, int y) {
        // create the panel
        this.setBounds(x, y, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    /**
     * This function will be triggered by the game thread.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long currentTime;
        // run this game as long as the gameThread exist
        while(this.gameThread.isAlive()) {
            // currentTime
            currentTime = System.nanoTime();
            // update the gamePanel if condition meet
            if ((currentTime - lastTime) >= DRAW_INTERVAL) {
                // monitor the keyBoard and update the direction of entitles
                updatePanel();
                // draw entitles
                repaint();
                // store the time into lastTime
                lastTime = currentTime;
            }
        }
    }

    /**
     * Draw player on the panel
     *
     * @param g2 you can think this like a drawing pencil
     */
    public void drawPlayer(Graphics2D g2) {
        this.player.draw(g2);
    }

    /**
     * Check the collision. If the tile is solid(collision is true), then the player/enemy can not go through the tile.
     *
     * @param entity player or enemy
     */
    public void checkCollisionTile(GameEntity entity) {
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
                topRow -= (entity.getSize() / UNIT_SIZE) - 1;
                isCollisionCheck1 = this.tiles[this.encodedWorld[leftCol][topRow]].isCollision();
                isCollisionCheck2 = this.tiles[this.encodedWorld[rightCol][topRow]].isCollision();
                if (isCollisionCheck1 || isCollisionCheck2) {
                    entity.setCollisionOn(true);
                }
            }
            case 'D' -> {
                bottomRow += entity.getSize() / UNIT_SIZE - 1;
                isCollisionCheck1 = this.tiles[this.encodedWorld[leftCol][bottomRow]].isCollision();
                isCollisionCheck2 = this.tiles[this.encodedWorld[rightCol][bottomRow]].isCollision();
                if (isCollisionCheck1 || isCollisionCheck2) {
                    entity.setCollisionOn(true);
                }
            }
            case 'L' -> {
                leftCol -= (entity.getSize() / UNIT_SIZE) - 1;
                isCollisionCheck1 = this.tiles[this.encodedWorld[leftCol][topRow]].isCollision();
                isCollisionCheck2 = this.tiles[this.encodedWorld[leftCol][bottomRow]].isCollision();
                if (isCollisionCheck1 || isCollisionCheck2) {
                    entity.setCollisionOn(true);
                }
            }
            case 'R' -> {
                rightCol += entity.getSize() / UNIT_SIZE - 1;
                isCollisionCheck1 = this.tiles[this.encodedWorld[rightCol][topRow]].isCollision();
                isCollisionCheck2 = this.tiles[this.encodedWorld[rightCol][bottomRow]].isCollision();
                if (isCollisionCheck1 || isCollisionCheck2) {
                    entity.setCollisionOn(true);
                }
            }
        }
    }

    /**
     * Check and see if the player is encounter an enemy. If yes, the index of the enemy will be returned.
     * If no, -1 will be returned.
     *
     * @param player a player instance
     * @param enemies a list of enemy
     * @return the index of the encounteredEnemy. Return -1 if we haven't encountered any.
     */
    public int checkAndGetEncounteredEnemyIndex(GameEntity player, ArrayList<GameEntity> enemies) {
        int index = -1;
        for (int i=0; i<enemies.size(); i++) {
            player.getSolidArea().x += player.getWorldX();
            player.getSolidArea().y += player.getWorldY();
            enemies.get(i).getSolidArea().x += enemies.get(i).getWorldX();
            enemies.get(i).getSolidArea().y += enemies.get(i).getWorldY();

            switch (player.getDirection()) {
                case 'U' -> {
                    player.getSolidArea().y -= enemies.get(i).getSpeed();
                    if (player.getSolidArea().intersects(enemies.get(i).getSolidArea())) {
                        player.setCollisionOn(true);
                        enemies.get(i).setCollisionOn(true);
                        index = i;
                    }
                }
                case 'D' -> {
                    player.getSolidArea().y += enemies.get(i).getSpeed();
                    if (player.getSolidArea().intersects(enemies.get(i).getSolidArea())) {
                        player.setCollisionOn(true);
                        enemies.get(i).setCollisionOn(true);
                        index = i;
                    }
                }
                case 'L' -> {
                    player.getSolidArea().x -= enemies.get(i).getSpeed();
                    if (player.getSolidArea().intersects(enemies.get(i).getSolidArea())) {
                        player.setCollisionOn(true);
                        enemies.get(i).setCollisionOn(true);
                        index = i;
                    }
                }
                case 'R' -> {
                    player.getSolidArea().x += enemies.get(i).getSpeed();
                    if (player.getSolidArea().intersects(enemies.get(i).getSolidArea())) {
                        player.setCollisionOn(true);
                        enemies.get(i).setCollisionOn(true);
                        index = i;
                    }
                }
            }
            player.resetSolidArea();
            enemies.get(i).resetSolidArea();

        }
        return index;
    }

    /**
     * Monitor the key by using the custom keyHandler and move player up/down/left/right in the world.
     */
    public void movePlayer() {
        // set the direction the player wants to go
        switch (this.myKeyHandler.direction()) {
            case 'U' -> this.player.setDirection('U');
            case 'D' -> this.player.setDirection('D');
            case 'L' -> this.player.setDirection('L');
            case 'R' -> this.player.setDirection('R');
        }
        // check the collision
        int enemyIndex = checkAndGetEncounteredEnemyIndex(this.player, this.enemies);
        if (enemyIndex != -1) {
            this.encounterEnemy = true;
            this.encounteredEnemyIndex = enemyIndex;
        }
        checkCollisionTile(this.player);
        // if no collision happened
        if (!this.player.isCollisionOn()) {
            switch (this.myKeyHandler.direction()) {
                case 'U' -> this.player.moveUp();
                case 'D' -> this.player.moveDown();
                case 'L' -> this.player.moveLeft();
                case 'R' -> this.player.moveRight();
            }
            // increase the spriteCounter to makes the player looks like running
            this.player.incrementSpriteCounter();
        }
    }

    /**
     * Randomly move the enemy up/down/left/right
     */
    public void moveEnemy() {
        for (GameEntity e: this.enemies) {
            e.incrementActionCounter();
            switch (e.getDirection()) {
                case ('U') -> {
                    checkCollisionTile(e);
                    if (!(e.isCollisionOn())) {
                        e.incrementSpriteCounter();
                        e.moveUp();
                    }
                }
                case ('D') -> {
                    checkCollisionTile(e);
                    if (!(e.isCollisionOn())) {
                        e.incrementSpriteCounter();
                        e.moveDown();
                    }
                }
                case ('L') -> {
                    checkCollisionTile(e);
                    if (!(e.isCollisionOn())) {
                        e.incrementSpriteCounter();
                        e.moveLeft();
                    }
                }
                case ('R') -> {
                    checkCollisionTile(e);
                    if (!(e.isCollisionOn())) {
                        e.incrementSpriteCounter();
                        e.moveRight();
                    }
                }
            }
            if (e.getActionCounter() == 100) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                if (i <= 25) {
                    e.setDirection('U');
                }
                if (i > 25 && i <= 50) {
                    e.setDirection('D');
                }
                if (i > 50 && i <= 75) {
                    e.setDirection('L');
                }
                if (i > 75 && i < 100) {
                    e.setDirection('R');
                }
                e.resetActionCounter();
            }
        }
    }

    /**
     * This method is being used to ensure we only paint the panel when we need to. Otherwise, the computer will always
     * paint the whole world even if it is outside the gamePanel. This method will improve the performance of our game.
     * This method will check the position of the player in the world and paint the surrounding items.
     *
     * @param worldX an integer represent the world's x-axis
     * @param worldY an integer represent the world's y-axis
     * @param player a gameEntity represent the player
     * @return a boolean which tells us whether we need to paint the panel.
     */
    private boolean paintIsNeeded(int worldX, int worldY, GameEntity player) {
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

    /**
     * Draw the world by using the tiles.
     *
     * @param g2 a drawing pencil.
     */
    public void drawWorld(Graphics2D g2) {
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
                BufferedImage tileImage = this.tiles[this.encodedWorld[col][row]].getImage();
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
}
