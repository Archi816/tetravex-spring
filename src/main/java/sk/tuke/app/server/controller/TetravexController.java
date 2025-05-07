package sk.tuke.app.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.app.core.Field;
import sk.tuke.app.core.Tile;
import sk.tuke.app.entity.Comment;
import sk.tuke.app.entity.Rating;
import sk.tuke.app.entity.Score;
import sk.tuke.app.service.*;

import java.util.Date;

@Controller
@RequestMapping("/tetravex")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class TetravexController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    private Field field;
    private int currentSize = -1;
    private String username;
    private Integer difficulty;
    private Long startTime;

    @RequestMapping("")
    public String tetravex(@RequestParam(value = "difficulty", required = false) Integer difficultyParam,
                           @RequestParam(value = "username", required = false) String usernameParam,
                           @RequestParam(value = "x1", required = false) Integer x1,
                           @RequestParam(value = "y1", required = false) Integer y1,
                           @RequestParam(value = "x2", required = false) Integer x2,
                           @RequestParam(value = "y2", required = false) Integer y2,
                           Model model) {

        if (usernameParam != null) {
            this.username = usernameParam;
        }

        if (difficultyParam != null) {
            this.difficulty = difficultyParam;
        }

        int size = (difficulty != null) ? difficulty : (currentSize > 0 ? currentSize : 3);

        if (field == null || size != currentSize) {
            field = Field.createField(size);
            currentSize = size;
            this.startTime = System.currentTimeMillis();
        }

        if (startTime == null) {
            this.startTime = System.currentTimeMillis();
        }

        if (field == null || size != currentSize) {
            field = Field.createField(size);
            currentSize = size;
        }

        if (x1 != null && y1 != null && x2 != null && y2 != null) {
            field.swapTiles(x1, y1, x2, y2);
        }

        boolean isSolved = field.isGameWon();

        model.addAttribute("username", this.username);
        model.addAttribute("startTime", startTime);
        model.addAttribute("difficulty", this.difficulty);
        model.addAttribute("htmlField", getHtmlField());
        model.addAttribute("isSolved", isSolved);
        model.addAttribute("scores", scoreService.getTopScores("Tetravex"));
        model.addAttribute("comments", commentService.getComments("Tetravex"));

        return "tetravex";
    }


    @RequestMapping("/new")
    public String newGame(@RequestParam(value = "username", required = false) String usernameParam,
                          @RequestParam(value = "difficulty", required = false) Integer difficultyParam,
                          Model model) {

        if (usernameParam != null) {
            this.username = usernameParam;
        }

        if (difficultyParam != null) {
            this.difficulty = difficultyParam;
        }


        field = Field.createField(difficultyParam);
        currentSize = difficultyParam;
        this.startTime = System.currentTimeMillis();

        model.addAttribute("username", this.username);
        model.addAttribute("startTime", startTime);
        model.addAttribute("difficulty", this.difficulty);
        model.addAttribute("htmlField", getHtmlField());
        model.addAttribute("isSolved", field.isGameWon());
        model.addAttribute("scores", scoreService.getTopScores("Tetravex"));
        model.addAttribute("comments", commentService.getComments("Tetravex"));

        return "tetravex";
    }


    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='tile-grid'>");

        for (int row = 0; row < field.getSize(); row++) {
            sb.append("<div class='tile-row'>");

            for (int col = 0; col < field.getSize(); col++) {
                Tile tile = field.getTile(row, col);
                sb.append(generateTileHtml(tile, row, col));
            }

            sb.append("</div>");
        }

        sb.append("</div>");
        return sb.toString();
    }


    private String generateTileHtml(Tile tile, int x, int y) {
        // Логика выбора цвета в зависимости от каждой цифры
        String colorClassTop = getColorClass(tile.getTop());
        String colorClassLeft = getColorClass(tile.getLeft());
        String colorClassRight = getColorClass(tile.getRight());
        String colorClassBottom = getColorClass(tile.getBottom());

        return String.format("""
        <div class="tile" data-x="%d" data-y="%d">
          <div class="tile-container">
            <div class="tile-block">
              <div class="tile-top tile-part %s"><p class="tile-number">%d</p></div>
              <div class="flex-between">
                <div class="tile-left tile-part %s"><p class="tile-number">%d</p></div>
                <div class="tile-right tile-part %s"><p class="tile-number">%d</p></div>
              </div>
              <div class="tile-bottom tile-part %s"><p class="tile-number">%d</p></div>
            </div>
            <span class="left-divider"></span>
            <span class="right-divider"></span>
          </div>
        </div>
    """, x, y, colorClassTop, tile.getTop(), colorClassLeft, tile.getLeft(), colorClassRight, tile.getRight(), colorClassBottom, tile.getBottom());
    }

    private String getColorClass(int value) {
        switch (value) {
            case 0:
                return "color-blue"; // Голубой для 0
            case 1:
                return "color-green"; // Зеленый для 1
            case 2:
                return "color-red"; // Красный для 2
            case 3:
                return "color-yellow"; // Желтый для 3
            case 4:
                return "color-purple"; // Фиолетовый для 4
            case 5:
                return "color-orange"; // Оранжевый для 5
            case 6:
                return "color-pink"; // Розовый для 6
            case 7:
                return "color-brown"; // Коричневый для 7
            case 8:
                return "color-teal"; // Бирюзовый для 8
            case 9:
                return "color-gray"; // Серый для 9
            default:
                return "color-default"; // Если по какой-то причине значение вне диапазона, можно добавить дефолтный цвет
        }
    }


    @RequestMapping("/comment")
    public String comment(String player, String comment, Model model) {
        try {
            commentService.addComment(new Comment( "Tetravex", player, comment, new Date()));
        } catch (CommentException e) {
            System.out.println(e.getMessage());
        }

        updateModel(model);
        return "tetravex";
    }

    @RequestMapping("/rating")
    public String rating(String player, int rating, Model model) {
        try {
            ratingService.setRating(new Rating("Tetravex", player, rating, new Date()));
        } catch (RatingException e) {
            System.out.println(e.getMessage());
        }

        updateModel(model);
        return "tetravex";
    }

    @RequestMapping("/score")
    public String score(String player, Model model) {
        if (field == null) {
            model.addAttribute("error", "Игра не была начата. Пожалуйста, начните новую игру.");
            updateModel(model);
            return "tetravex";
        }

        int finalScore = field.getScore();
        //int playingTime = field.getPlayingTime();

        try {
            scoreService.addScore(new Score("Tetravex", player, finalScore, new Date()));
        } catch (ScoreException e) {
            System.out.println(e.getMessage());
        }

        updateModel(model);
        return "tetravex";
    }


    private void updateModel(Model model) {
        model.addAttribute("username", this.username);
        model.addAttribute("startTime", startTime);
        model.addAttribute("difficulty", this.difficulty);
        model.addAttribute("htmlField", getHtmlField());
        model.addAttribute("isSolved", field.isGameWon());

        try {
            model.addAttribute("scores", scoreService.getTopScores("Tetravex"));
        } catch (ScoreException e) {
            System.out.println(e.getMessage());
        }

        try {
            model.addAttribute("comments", commentService.getComments("Tetravex"));
        } catch (CommentException e) {
            System.out.println(e.getMessage());
        }

        try {
            model.addAttribute("averageRating", ratingService.getAverageRating("Tetravex"));
        } catch (RatingException e) {
            System.out.println(e.getMessage());
        }
    }

}
