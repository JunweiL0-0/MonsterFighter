package main.java.utilities;

public class CollisionChecker {
    private final int[][] encodedMap;
    private final MapDecoder mapDecoder;


    public CollisionChecker(int[][] encodedMap, MapDecoder mapDecoder) {
        this.encodedMap = encodedMap;
        this.mapDecoder = mapDecoder;
    }

    public void checkCollision(GameEntity entity) {
        int tileSize = 32;
        int leftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int rightWorldX = leftWorldX + entity.getSolidArea().width;
        int topWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int bottomWorldY = topWorldY + entity.getSolidArea().height;

        int leftCol = leftWorldX/tileSize;
        int rightCol = rightWorldX/tileSize;
        int topRow = topWorldY/tileSize;
        int bottomRow = bottomWorldY/tileSize;

        int encodedInteger1;
        int encodedInteger2;
        Tile tile1, tile2;
        switch (entity.getDirection()) {
            case 'U':
                topRow -= entity.getSize()/tileSize;
                encodedInteger1 = this.encodedMap[leftCol][topRow];
                encodedInteger2 = this.encodedMap[rightCol][topRow];
                tile1 = this.mapDecoder.decode(encodedInteger1);
                tile2 = this.mapDecoder.decode(encodedInteger2);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            case 'D':
                bottomRow += entity.getSize()/tileSize;
                encodedInteger1 = this.encodedMap[leftCol][bottomRow];
                encodedInteger2 = this.encodedMap[rightCol][bottomRow];
                tile1 = this.mapDecoder.decode(encodedInteger1);
                tile2 = this.mapDecoder.decode(encodedInteger2);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            case 'L':
                leftCol -= entity.getSize()/tileSize;
                encodedInteger1 = this.encodedMap[leftCol][topRow];
                encodedInteger2 = this.encodedMap[leftCol][bottomRow];
                tile1 = this.mapDecoder.decode(encodedInteger1);
                tile2 = this.mapDecoder.decode(encodedInteger2);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            case 'R':
                rightCol += entity.getSize()/tileSize;
                encodedInteger1 = this.encodedMap[rightCol][topRow];
                encodedInteger2 = this.encodedMap[rightCol][bottomRow];
                tile1 = this.mapDecoder.decode(encodedInteger1);
                tile2 = this.mapDecoder.decode(encodedInteger2);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
        }

    }
}
