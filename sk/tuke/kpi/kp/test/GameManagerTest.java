package sk.tuke.kpi.kp.test;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.src.tetravex.GameManager;
import sk.tuke.kpi.kp.src.tetravex.Tile;
import static org.junit.jupiter.api.Assertions.*;

public class GameManagerTest {

    @Test
    void testGridInitialization() {
        GameManager gameManager = new GameManager(3);
        assertNotNull(gameManager);
    }

    @Test
    void testTilePlacement() {
        GameManager gameManager = new GameManager(3);
        Tile tile = new Tile(1, 2, 3, 4);
        assertTrue(gameManager.placeTile(tile, 0, 0));
    }

    @Test
    void testInvalidTilePlacement() {
        GameManager gameManager = new GameManager(3);
        Tile tile1 = new Tile(1, 2, 3, 4);
        Tile tile2 = new Tile(5, 6, 7, 8);

        gameManager.placeTile(tile1, 0, 0);
        assertFalse(gameManager.placeTile(tile2, 0, 1));
    }

    @Test
    void testWinningCondition() {
        GameManager gameManager = new GameManager(3);

        Tile tile1 = new Tile(1, 1, 2, 3);
        Tile tile2 = new Tile(2, 7, 4, 1);
        Tile tile3 = new Tile(3, 5, 6, 7);
        Tile tile4 = new Tile(2, 2, 5, 6);
        Tile tile5 = new Tile(4, 3, 7, 2);
        Tile tile6 = new Tile(6, 1, 8, 3);
        Tile tile7 = new Tile(5, 9, 1, 5);
        Tile tile8 = new Tile(7, 4, 9, 9);
        Tile tile9 = new Tile(8, 6, 3, 4);

        gameManager.placeTile(tile1, 0, 0);
        gameManager.placeTile(tile2, 0, 1);
        gameManager.placeTile(tile3, 0, 2);
        gameManager.placeTile(tile4, 1, 0);
        gameManager.placeTile(tile5, 1, 1);
        gameManager.placeTile(tile6, 1, 2);
        gameManager.placeTile(tile7, 2, 0);
        gameManager.placeTile(tile8, 2, 1);
        gameManager.placeTile(tile9, 2, 2);

        assertTrue(gameManager.checkWinCondition());
    }
}

