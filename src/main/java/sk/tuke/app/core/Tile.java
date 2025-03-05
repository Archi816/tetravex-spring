package sk.tuke.app.core;

public class Tile {
    private int top;
    private int right;
    private int bottom;
    private int left;
    private boolean placed;

    public Tile(int top, int right, int bottom, int left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.placed = false;
    }

    public int getTop() { return top; }
    public int getRight() { return right; }
    public int getBottom() { return bottom; }
    public int getLeft() { return left; }
    public boolean isPlaced() { return placed; }
    public void setPlaced(boolean placed) { this.placed = placed; }
}


