package main.java.utilities;

import main.java.utilities.*;
import main.java.utilities.map.MapLoader;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // constant value
    private static final double FPS = 80.0; // FPS
    private static final double DRAW_INTERVAL = 1000000000/FPS; // calculate the 80 fps time
    private static final int MAX_MAP_COL = 50;
    private static final int MAX_MAP_ROW = 50;
    // variable
    private final Player player;
    private final MapDrawer mapDrawer;
    private final Thread gameThread;


    public GamePanel(int x, int y, int screenWidth, int screenHeight) {
        // init the panel
        initPanel(x, y, screenWidth, screenHeight);
        // get the encodedMap
        int[][] encodedMap = new MapLoader().getEncodedMap();
        MapDecoder mapDecoder = new MapDecoder();
        // Monitor the player movement
        MyKeyHandler kHand = new MyKeyHandler();
        CollisionChecker collisionChecker = new CollisionChecker(encodedMap, mapDecoder);
        // 25*32: the middle of the game map
        this.player = new Player(kHand, collisionChecker, 25*32, 25*32, screenWidth, screenHeight);
        // Creating the World map
        this.mapDrawer = new MapDrawer(this.player, encodedMap, mapDecoder);
        // listen key pressing event
        this.addKeyListener(kHand);
        // Threading
        this.gameThread = new Thread(this); // create a new thread
        this.gameThread.start(); // this will auto call the run function in this class
    }

    private void initPanel(int x, int y, int screenWidth, int screenHeight) {
        // create the panel
        this.setBounds(x, y, screenWidth, screenHeight);
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
        // update player
        this.player.update();
    }

    public void paintComponent(Graphics g) {
        // this will be called by the rePaint()
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast it
        // tile should be displayed before player
        this.mapDrawer.draw(g2); // draw the map
        this.player.draw(g2); // draw the player
        g2.dispose(); // dispose of this g2 and release system resources
    }
}
