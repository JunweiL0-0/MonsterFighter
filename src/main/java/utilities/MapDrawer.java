package main.java.utilities;

import java.awt.*;

public class MapDrawer {
    private final Player player;
    // double array for storing the map
    private final int[][] encodedMap;
    private final MapDecoder mapDecoder;


    public MapDrawer(Player player, int[][] encodedMap, MapDecoder mapDecoder) {
        this.player = player;
        this.encodedMap = encodedMap;
        this.mapDecoder = mapDecoder;
    }

    public void draw(Graphics2D g2) {
        int maxMapCol = this.encodedMap[0].length;
        int maxMapRow = this.encodedMap[1].length;
        // col and row in the map
        int col = 0;
        int row = 0;
        // draw the map
        while (row < maxMapRow) {
            // get the encoded integer
            int encodedInteger = this.encodedMap[col][row];
            // decode the encoded integer and get the tile
            Tile tile = this.mapDecoder.decode(encodedInteger);
            int tileSize = tile.getSize();
            // top left point's x and y
            int worldX = col * tileSize;
            int worldY = row * tileSize;
            // put the user in the middle of the world
            int screenX = worldX - this.player.getWorldX() + this.player.getScreenX();
            int screenY = worldY - this.player.getWorldY() + this.player.getScreenY();
            // only paint the map when we need to
            if (paintIsNeeded(worldX, worldY, tileSize, this.player)) {
                // draw on the panel
                g2.drawImage(tile.getImage(), screenX, screenY, tileSize, tileSize, null);
            }
            // goes to next col
            col++;
            // goes to next row if we run out of col
            if (col == maxMapCol) {
                // reset the col, goes to next row
                col = 0;
                row++;
            }
        }
    }

    private boolean paintIsNeeded(int worldX, int worldY, int tileSize, Player player) {
        // we add/subtract one tileSize to adjust our screen
        // check it withinScreenWidth
        boolean condition1 = (worldX + tileSize) > player.getWorldX() - player.getScreenX();
        boolean condition2 = (worldX - tileSize) < player.getWorldX() + player.getScreenX();
        // check it withinScreenHeight
        boolean condition3 = (worldY + tileSize) > player.getWorldY() - player.getScreenY();
        boolean condition4 = (worldY - tileSize) < player.getWorldY() + player.getScreenY();
        // return true if within the screen
        return condition1 && condition2 && condition3 && condition4;
    }
}
