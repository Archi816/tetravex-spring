package sk.tuke.kpi.kp.src.tetravex;

public class Grid {
    private int size;
    private Tile[][] tiles;

    public Grid(int size) {
        this.size = size;
        this.tiles = new Tile[size][size];
    }

    public boolean placeTile(Tile tile, int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size && tiles[x][y] == null) {
            tiles[x][y] = tile;
            tile.setPlaced(true);
            return true;
        }
        return false;
    }

    public boolean isPlacementValid(Tile tile, int x, int y) {
        if (x > 0 && tiles[x - 1][y] != null && tiles[x - 1][y].getBottom() != tile.getTop()) return false;
        if (y > 0 && tiles[x][y - 1] != null && tiles[x][y - 1].getRight() != tile.getLeft()) return false;
        if (x < size - 1 && tiles[x + 1][y] != null && tiles[x + 1][y].getTop() != tile.getBottom()) return false;
        if (y < size - 1 && tiles[x][y + 1] != null && tiles[x][y + 1].getLeft() != tile.getRight()) return false;
        return true;
    }

    public boolean isGameWon() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }
}
