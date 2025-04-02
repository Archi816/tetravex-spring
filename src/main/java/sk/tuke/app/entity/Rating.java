package sk.tuke.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rating.getRatings", query = "SELECT r FROM Rating r WHERE r.game=:game ORDER BY r.ratedOn DESC"),
        @NamedQuery(name = "Rating.getRating", query = "SELECT r.rating FROM Rating r WHERE r.game=:game AND r.player=:player"),
        @NamedQuery(name = "Rating.getAverageRating", query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game=:game"),
        @NamedQuery(name = "Rating.resetRatings", query = "DELETE FROM Rating r")
})
public class Rating implements Serializable {
    @Id
    @GeneratedValue
    private int ident;

    private String game;
    private String player;
    private int rating;

    @Column(name = "rated_on", nullable = false)
    private Date ratedOn;

    public Rating() {}

    public Rating(String game, String player, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }
    public String getGame() { return game; }
    public void setGame(String game) { this.game = game; }
    public String getPlayer() { return player; }
    public void setPlayer(String player) { this.player = player; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public Date getRatedOn() { return ratedOn; }
    public void setRatedOn(Date ratedOn) { this.ratedOn = ratedOn; }
}
