package sk.tuke.app;

import org.junit.jupiter.api.Test;
import sk.tuke.app.consoleUI.ConsoleUI;
import sk.tuke.app.core.Field;
import sk.tuke.app.core.GameManager;
import sk.tuke.app.core.Tile;
import sk.tuke.app.service.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for simple App.
 * IMPORTANT!!!! Before using test you must comment 2 lines in Field.java
 * public Field(int size) {
 *         this.size = size;
 *         this.tiles = new Tile[size][size];
 *         //generateRandomTiles();
 *         //generateSolvedGrid();
 *         //shuffleGrid();
 *     }
 *     here is example
 */
public class SpringClientTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    void testGridInitialization() {
        GameManager gameManager = new GameManager(3);
        assertNotNull(gameManager);
    }

    @Test
    void testFieldInitialization() {
        Field field = new Field(3); // Create a 3x3 field
        assertNotNull(field);
    }

    @Test
    void testTilePlacement() {
        Field field = new Field(3);
        Tile tile = new Tile(1, 2, 3, 4);
        assertTrue(field.placeTile(tile, 0, 0));
    }

    @Test
    void testInvalidTilePlacement() {
        Field field = new Field(3);
        Tile tile1 = new Tile(1, 2, 3, 4);
        Tile tile2 = new Tile(5, 6, 7, 8);

        field.placeTile(tile1, 0, 0);
        assertFalse(field.placeTile(tile2, 0, 0));
    }

    @Test
    void testGridWinCondition() {
        Field field = new Field(3);

        Tile tile1 = new Tile(1, 1, 2, 3);
        Tile tile2 = new Tile(2, 7, 4, 1);
        Tile tile3 = new Tile(3, 5, 6, 7);
        Tile tile4 = new Tile(2, 2, 5, 6);
        Tile tile5 = new Tile(4, 3, 7, 2);
        Tile tile6 = new Tile(6, 1, 8, 3);
        Tile tile7 = new Tile(5, 9, 1, 5);
        Tile tile8 = new Tile(7, 4, 9, 9);
        Tile tile9 = new Tile(8, 6, 3, 4);

        field.placeTile(tile1, 0, 0);
        field.placeTile(tile2, 0, 1);
        field.placeTile(tile3, 0, 2);
        field.placeTile(tile4, 1, 0);
        field.placeTile(tile5, 1, 1);
        field.placeTile(tile6, 1, 2);
        field.placeTile(tile7, 2, 0);
        field.placeTile(tile8, 2, 1);
        field.placeTile(tile9, 2, 2);

        assertTrue(field.isGameWon());
    }

    @Test
    void testFieldPrinting() {
        GameManager gameManager = new GameManager(3);

        ScoreService scoreService = new ScoreServiceJPA();
        RatingService ratingService = new RatingServiceJPA();
        CommentService commentService = new CommentServiceJPA();

        ConsoleUI consoleUI = new ConsoleUI(gameManager.getField(), scoreService, ratingService, commentService);

        Tile tile1 = new Tile(1, 2, 3, 4);
        Tile tile2 = new Tile(5, 6, 7, 8);
        Tile tile3 = new Tile(9, 0, 2, 3);
        Tile tile4 = new Tile(9, 3, 1, 5);
        Tile tile5 = new Tile(1, 2, 3, 4);
        Tile tile6 = new Tile(5, 6, 7, 8);
        Tile tile7 = new Tile(9, 0, 2, 3);
        Tile tile8 = new Tile(9, 3, 1, 5);
        Tile tile9 = new Tile(9, 3, 1, 5);

        gameManager.placeTile(tile1, 0, 0);
        gameManager.placeTile(tile2, 0, 1);
        gameManager.placeTile(tile3, 0, 2);
        gameManager.placeTile(tile4, 1, 0);
        gameManager.placeTile(tile5, 1, 1);
        gameManager.placeTile(tile6, 1, 2);
        gameManager.placeTile(tile7, 2, 0);
        gameManager.placeTile(tile8, 2, 1);
        gameManager.placeTile(tile9, 2, 2);
    }

}
