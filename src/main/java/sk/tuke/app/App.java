package sk.tuke.app;

import sk.tuke.app.consoleUI.ConsoleUI;
import sk.tuke.app.core.Field;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to Tetravex!\n");

        int size = selectDifficulty();
        Field field = new Field(size);

        ConsoleUI consoleUI = new ConsoleUI(field);
        consoleUI.play();
    }

    private static int selectDifficulty() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select difficulty level:");
            System.out.println("1. Easy (3x3)");
            System.out.println("2. Medium (4x4)");
            System.out.println("3. Hard (6x6)");
            System.out.println("4. Impossible (10x10)");
            System.out.print("Enter choice (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": return 3;
                case "2": return 4;
                case "3": return 6;
                case "4": return 10;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
}
