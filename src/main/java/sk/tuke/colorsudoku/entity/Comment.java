package sk.tuke.colorsudoku.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery( name = "Comment.getComments", query = "SELECT c FROM Comment c WHERE c.game=:game ORDER BY c.comment DESC"),
        @NamedQuery( name = "Comment.reset", query = "DELETE FROM Comment ")
})
public class Comment implements Serializable{
    @Id
    @GeneratedValue
    private int ident;

    private String game;

    private String player;

    private String comment;

    private Date commentedOn;

    public Comment(){}

    public Comment(String game, String player, String comment, Date commentedOn) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date playedOn) {
        this.commentedOn = playedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "ident=" + ident +
                ", game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", comment=" + comment +
                ", commentedOn=" + commentedOn +
                '}';
    }
}
