package sk.tuke.app.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sk.tuke.app.entity.Comment;
import sk.tuke.app.entity.Score;
import sk.tuke.app.service.CommentService;
import sk.tuke.app.service.ScoreService;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/rules")
    public String rules() {
        return "rules";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        List<Score> scores = scoreService.getTopScores("Tetravex");
        model.addAttribute("scores", scores);
        return "leaderboard";
    }

    @GetMapping("/comment")
    public String getComments(Model model) {
        List<Comment> comments = commentService.getComments("Tetravex");
        model.addAttribute("comments", comments);
        return "comment";
    }
}
