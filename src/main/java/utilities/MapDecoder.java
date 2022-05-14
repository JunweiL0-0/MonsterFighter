package main.java.utilities;

import main.java.utilities.map.MapLoader;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MapDecoder {
    private static final String TILE_IMAGE_PATH = "src/main/java/image/tile/";
    private final Tile[] tile;


    public MapDecoder() {
        this.tile = new Tile[6];
        getTileAndSetImage();
        setCollision();
    }

    public Tile decode(int encodedInteger) {
        return tile[encodedInteger];
    }

    private void getTileAndSetImage() {
        try {
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "grass.png")));
            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "earth.png")));
            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "water.png")));
            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "sand.png")));
            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "tree.png")));
            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(new File(TILE_IMAGE_PATH + "wall.png")));
        } catch (IOException e) {
            System.out.println("Loading image error");
        }
    }

    private void setCollision() {
        // water, tree, wall
        tile[2].setCollision(true);
        tile[4].setCollision(true);
        tile[5].setCollision(true);
    }
}
