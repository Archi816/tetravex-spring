package sk.tuke.app.consoleUI;

import sk.tuke.app.core.Field;
import sk.tuke.app.entity.Comment;
import sk.tuke.app.entity.Score;
import sk.tuke.app.entity.Rating;
import sk.tuke.app.service.*;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private Field field;
    private final Scanner scanner = new Scanner(System.in);
    private static final Pattern INPUT_PATTERN = Pattern.compile("([A-Z])([1-9])\\s([A-Z])([1-9])");
    private long startTime;
    private final ScoreService scoreService;
    private final RatingService ratingService;
    private final CommentService commentService;
    private String gameName;
    private String playerName;
    private int moveCount;
    private int difficulty;

    public ConsoleUI(Field field, ScoreService scoreService, RatingService ratingService, CommentService commentService) {
        this.field = field;
        this.scoreService = scoreService;
        this.ratingService = ratingService;
        this.commentService = commentService;
    }

    public void play() {
        initializeGame();

        while (!field.isGameWon()) {
            FieldRender.printField(field, System.currentTimeMillis() - startTime);
            processInput();
            moveCount++;
        }

        handleGameEnd();
    }

    private void initializeGame() {
        System.out.print("Enter your name: ");
        playerName = scanner.nextLine().trim();
        gameName = "Tetravex";
        difficulty = field.getSize();
        moveCount = 0;
        startTime = System.currentTimeMillis();
    }

    private void handleGameEnd() {
        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("Congratulations! You solved the puzzle!");
        System.out.println("Time taken: " + timeTaken + " seconds");
        System.out.println("Total moves: " + moveCount);

        int scoreValue = calculateScore(difficulty, timeTaken, moveCount);
        scoreService.addScore(new Score(gameName, playerName, scoreValue, new java.util.Date()));

        askForRating();
        askForComment();
        askPlayAgain();
    }

    private void askPlayAgain() {
        System.out.print("Play again? (Y/n): ");
        String input = scanner.nextLine().trim().toUpperCase();

        while (true) {
            if (input.equals("Y")) {
                System.out.println("Starting a new game...");
                startTime = System.currentTimeMillis();
                field = Field.createField(field.getSize());
                play();
            } else if (input.equals("N")) {
                System.out.println("Thanks for playing!");
                System.exit(0);
            } else {
                System.out.print("Invalid input! Please enter 'Y' or 'N': ");
                input = scanner.nextLine().trim().toUpperCase();
            }
        }
    }

    private void askForRating() {
        System.out.print("Rate the game (1-5): ");
        int ratingValue;
        while (true) {
            try {
                ratingValue = Integer.parseInt(scanner.nextLine().trim());
                if (ratingValue < 1 || ratingValue > 5) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid rating! Enter a number between 1 and 5: ");
            }
        }
        ratingService.setRating(new Rating(gameName, playerName, ratingValue, new java.util.Date()));
    }

    private void askForComment() {
        System.out.print("Leave a comment about the game: ");
        String commentText = scanner.nextLine().trim();
        if (!commentText.isEmpty()) {
            commentService.addComment(new Comment(gameName, playerName, commentText, new java.util.Date()));
        }
    }

    private void processInput() {
        System.out.print("\nEnter move (e.g., A1 B2 to swap tiles) \n'X' to exit\n'R' to restart: ");
        String input = scanner.nextLine().trim().toUpperCase();

        switch (input) {
            case "X":
                System.out.println("Game exited.");
                System.exit(0);
                break;
            case "R":
                restartGame();
                break;
            case "T":
                showTopScores();
                break;
            case "C":
                showComments();
                break;
            case "V":
                showAverageRating();
                break;
            default:
                handleTileSwap(input);
                break;
        }
    }

    private void restartGame() {
        System.out.println("Game restarted.");
        startTime = System.currentTimeMillis();
        field = Field.createField(field.getSize());
        play();
    }

    private void showTopScores() {
        List<Score> scores = scoreService.getTopScores(gameName);
        System.out.println("Top Scores:");
        scores.forEach(score -> System.out.println(score.getPlayer() + " - " + score.getPoints()));
    }

    private void showComments() {
        List<Comment> comments = commentService.getComments(gameName);
        if (comments.isEmpty()) {
            System.out.println("No comments yet.");
        } else {
            System.out.println("Game Comments:");
            comments.forEach(comment -> {
                System.out.println("Player: " + comment.getPlayer());
                System.out.println("Comment: " + comment.getComment());
                System.out.println("Commented on: " + comment.getCommentedOn());
                System.out.println("------------------------------");
            });
        }
    }

    private void showAverageRating() {
        int averageRating = ratingService.getAverageRating(gameName);
        System.out.println("Average Rating: " + averageRating);
    }

    private void handleTileSwap(String input) {
        var matcher = INPUT_PATTERN.matcher(input);
        if (matcher.matches()) {
            int row1 = matcher.group(1).charAt(0) - 'A';
            int col1 = Integer.parseInt(matcher.group(2)) - 1;
            int row2 = matcher.group(3).charAt(0) - 'A';
            int col2 = Integer.parseInt(matcher.group(4)) - 1;

            if (field.getTile(row1, col1) != null && field.getTile(row2, col2) != null) {
                field.swapTiles(row1, col1, row2, col2);
            } else {
                System.out.println("Invalid move! One of the positions is empty.");
            }
        } else {
            System.out.println("Invalid input! Use format 'A1 B2' to swap tiles.");
        }
    }

    private int calculateScore(int difficulty, long timeTaken, int moveCount) {
        int baseScore = 5000; // Base score to start with
        double difficultyMultiplier;

        switch (difficulty) {
            case 3: difficultyMultiplier = 1.0; break;
            case 4: difficultyMultiplier = 1.2; break;
            case 6: difficultyMultiplier = 1.5; break;
            case 10: difficultyMultiplier = 2.0; break;
            default: difficultyMultiplier = 1.0;
        }

        int timePenalty = (int) timeTaken * 2;
        int movePenalty = moveCount * 5;

        int finalScore = (int) (baseScore * difficultyMultiplier) - timePenalty - movePenalty;
        return Math.max(finalScore, 0);
    }

    private String formatTime(long miliseconds) {
        long seconds = miliseconds / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}

