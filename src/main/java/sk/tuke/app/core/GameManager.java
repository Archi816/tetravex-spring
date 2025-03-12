package sk.tuke.app.core;

import java.util.Random;

public class GameManager {
    private Field field;
    private Tile[] tiles;

    public GameManager(int size) {
        this.field = new Field(size);
        this.tiles = new Tile[size * size];
        generateTiles();
    }

    private void generateTiles() {
        Random rand = new Random();
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile(rand.nextInt(10), rand.nextInt(10), rand.nextInt(10), rand.nextInt(10));
        }
    }

    public boolean placeTile(Tile tile, int x, int y) {
        if (field.isValidPosition(x, y)) {
            return field.placeTile(tile, x, y);
        }
        return false;
    }

    public Field getField() {
        return field;
    }

    public boolean checkWinCondition() {
        return field.isGameWon();
    }
}


