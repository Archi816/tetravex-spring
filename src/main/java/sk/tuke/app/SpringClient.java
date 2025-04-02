package sk.tuke.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import sk.tuke.app.consoleUI.ConsoleUI;
import sk.tuke.app.core.Field;
import sk.tuke.app.service.ScoreService;
import sk.tuke.app.service.ScoreServiceJPA;
import sk.tuke.app.service.RatingService;
import sk.tuke.app.service.RatingServiceJPA;
import sk.tuke.app.service.CommentService;
import sk.tuke.app.service.CommentServiceJPA;

import java.util.Scanner;

@SpringBootApplication
@Configuration
public class SpringClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI) {
        return args -> consoleUI.play();
    }

    @Bean
    public ConsoleUI consoleUI(Field field, ScoreService scoreService, RatingService ratingService, CommentService commentService) {
        return new ConsoleUI(field, scoreService, ratingService, commentService);
    }

    @Bean
    public Field field() {
        int size = selectDifficulty();
        return new Field(size);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }

    private int selectDifficulty() {
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
