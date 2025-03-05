package sk.tuke.app.core;

import java.util.Random;

public class GameManager {
    private Grid grid;
    private Tile[] tiles;

    public GameManager(int size) {
        this.grid = new Grid(size);
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
        if (grid.isPlacementValid(tile, x, y)) {
            return grid.placeTile(tile, x, y);
        }
        return false;
    }

    public boolean checkWinCondition() {
        return grid.isGameWon();
    }
}


