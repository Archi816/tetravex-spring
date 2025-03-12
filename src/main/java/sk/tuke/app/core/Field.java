package sk.tuke.app.core;

import java.util.Random;

public class Field {
    private final int size;
    private final Tile[][] tiles;

    public Field(int size) {
        this.size = size;
        this.tiles = new Tile[size][size];
        //generateRandomTiles();
        generateSolvedGrid();
        shuffleGrid();
    }

    public static Field createField(int size) {
        Field newField = new Field(size);
        newField.generateSolvedGrid();
        newField.shuffleGrid();
        return newField;
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            return tiles[x][y];
        }
        return null;
    }

    public boolean placeTile(Tile tile, int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size && tiles[x][y] == null) {
            tiles[x][y] = tile;
            return true;
        }
        return false;
    }

    public void swapTiles(int x1, int y1, int x2, int y2) {
        if (isValidPosition(x1, y1) && isValidPosition(x2, y2)) {
            Tile temp = tiles[x1][y1];
            tiles[x1][y1] = tiles[x2][y2];
            tiles[x2][y2] = temp;
        }
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public boolean isGameWon() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile tile = tiles[i][j];

                if (tile == null) {
                    return false;
                }

                if (i > 0 && tile.getTop() != tiles[i - 1][j].getBottom()) {
                    return false;
                }
                if (j > 0 && tile.getLeft() != tiles[i][j - 1].getRight()) {
                    return false;
                }
                if (i < size - 1 && tile.getBottom() != tiles[i + 1][j].getTop()) {
                    return false;
                }
                if (j < size - 1 && tile.getRight() != tiles[i][j + 1].getLeft()) {
                    return false;
                }
            }
        }

        return true;
    }


    private void generateSolvedGrid() {
        Random rand = new Random();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int bottom = (row == size - 1) ? rand.nextInt(10) : rand.nextInt(10);
                int right = (col == size - 1) ? rand.nextInt(10) : rand.nextInt(10);
                int top = (row == 0) ? rand.nextInt(10) : tiles[row - 1][col].getBottom();
                int left = (col == 0) ? rand.nextInt(10) : tiles[row][col - 1].getRight();

                tiles[row][col] = new Tile(top, right, bottom, left);
            }
        }
    }

    public void generateRandomTiles() {
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int top = rand.nextInt(10);
                int left = rand.nextInt(10);
                int bottom = rand.nextInt(10);
                int right = rand.nextInt(10);

                tiles[i][j] = new Tile(top, left, right, bottom);
            }
        }
    }

    private void shuffleGrid() {
        Random rand = new Random();
        for (int i = 0; i < size * size; i++) {
            int x1 = rand.nextInt(size);
            int y1 = rand.nextInt(size);
            int x2 = rand.nextInt(size);
            int y2 = rand.nextInt(size);

            Tile temp = tiles[x1][y1];
            tiles[x1][y1] = tiles[x2][y2];
            tiles[x2][y2] = temp;
        }
    }
}

