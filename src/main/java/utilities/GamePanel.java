package main.java.utilities;

import main.java.controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // constant value
    public static final int SCREEN_WIDTH = 560;
    public static final int SCREEN_HEIGHT = 280;
    public static final int PLAYER_SCREEN_X = ((SCREEN_WIDTH/2) - (Player.PLAYER_SIZE/2));
    public static final int PLAYER_SCREEN_Y = ((SCREEN_HEIGHT/2) - (Player.PLAYER_SIZE/2));
    public static final double FPS = 80.0; // FPS
    public static final double DRAW_INTERVAL = 1000000000/FPS; // calculate the 80 fps time
    // variable
    private final GameController gc;
    private final Player player;
    private final Enemy[] enemies;
    private final World world;
    private final Thread gameThread;


    public GamePanel(int x, int y, GameController gc) {
        this.gc = gc;
        // init the panel
        placePanel(x, y);
        // get the encodedMap
        // Monitor the player movement
        MyKeyHandler kHand = new MyKeyHandler();
        // 25*32: the middle of the game map
        this.player = new Player();
        // Creating the World map
        this.enemies = new Enemy[1];
        enemies[0] = new Enemy(World.PLAYER_DEFAULT_WORLD_X-300, World.PLAYER_DEFAULT_WORLD_Y-2, 1, World.UNIT_SIZE, 1, this.player);
        this.world = new World(this.player, enemies, kHand, this);
        // listen key pressing event
        this.addKeyListener(kHand);
        // Threading
        this.gameThread = new Thread(this); // create a new thread
        this.gameThread.start(); // this will auto call the run function in this class
    }

    private void placePanel(int x, int y) {
        // create the panel
        this.setBounds(x, y, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        // run this game as long as the gameThread exist
        while(this.gameThread.isAlive()) {
            // currentTime
            currentTime = System.nanoTime();
            // update the gamePanel if condition meet
            if ((currentTime - lastTime) >= DRAW_INTERVAL) {
                // monitor the keyBoard and update the direction of entitles
                update();
                // draw entitles
                repaint();
                // store the time into lastTime
                lastTime = currentTime;
            }
        }
    }

    public void update() {
        // update world
        this.world.update();
    }

    public void paintComponent(Graphics g) {
        // this will be called by the rePaint()
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast it
        // tile should be displayed before player
        this.world.draw(g2); // draw the map
        this.player.draw(g2); // draw the player
        this.enemies[0].draw(g2);
        g2.dispose(); // dispose of this g2 and release system resources
    }

    public void storeBattleIndex(int battleInt) {
        this.gc.storeBattleIndex(battleInt);
    }
    public void startTheWorld() {
        this.world.start();
    }
}
