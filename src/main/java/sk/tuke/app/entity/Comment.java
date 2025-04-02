package sk.tuke.app.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Comment.getComments", query = "SELECT c FROM Comment c WHERE c.game=:game ORDER BY c.commentedOn DESC"),
        @NamedQuery(name = "Comment.resetComments", query = "DELETE FROM Comment c")
})
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ident;

    private String game;
    private String player;
    private String comment;

    @Column(name = "commented_on", nullable = false)
    private Date commentedOn;

    public Comment() {}

    public Comment(String game, String player, String comment, Date commentedOn) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }
    public String getGame() { return game; }
    public void setGame(String game) { this.game = game; }
    public String getPlayer() { return player; }
    public void setPlayer(String player) { this.player = player; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Date getCommentedOn() { return commentedOn; }
    public void setCommentedOn(Date commentedOn) {this.commentedOn = commentedOn;}
}
