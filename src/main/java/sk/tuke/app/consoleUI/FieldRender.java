package sk.tuke.app.consoleUI;

import sk.tuke.app.core.Field;
import sk.tuke.app.core.Tile;

public class FieldRender {
    public static void printField(Field field, long elapsedTime) {
        System.out.println("\nCurrent Field State:");
        System.out.println("Time elapsed: " + formatTime(elapsedTime));
        System.out.println("Enter 'T' to view top scores, 'C' for comments, 'V' for average rating.");

        System.out.print("    ");
        for (int col = 0; col < field.getSize(); col++) {
            System.out.print("    " + (col + 1) + "   ");
        }
        System.out.println("\n");

        for (int row = 0; row < field.getSize(); row++) {
            System.out.print("    ");
            for (int col = 0; col < field.getSize(); col++) {
                Tile tile = field.getTile(row, col);
                System.out.print(tile != null ? " |  " + tile.getTop() + "  |" : "       ");
            }
            System.out.println();

            System.out.print((char) ('A' + row) + "    ");
            for (int col = 0; col < field.getSize(); col++) {
                Tile tile = field.getTile(row, col);
                System.out.print(tile != null ? "|" + tile.getLeft() + " x " + tile.getRight() + "| " : "        ");
            }
            System.out.println();

            System.out.print("    ");
            for (int col = 0; col < field.getSize(); col++) {
                Tile tile = field.getTile(row, col);
                System.out.print(tile != null ? " |  " + tile.getBottom() + "  |" : "       ");
            }
            System.out.println("\n");
        }
    }

    private static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
