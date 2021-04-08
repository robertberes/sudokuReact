package sk.tuke.colorsudoku.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;

@Entity
//@NamedQuery( name = "Rating.getAverageRating",
//        query = "SELECT r FROM Rating r WHERE r.game=:game ORDER BY r.rating DESC")
//@NamedQuery( name = "Score.reset",
//        query = "DELETE FROM Rating ")
public class Rating implements Serializable{
    @Id
    @GeneratedValue
    private int ident;

    private String game;

    private String player;

    private int rating;

    private Date ratedOn;

    public Rating(){}

    public Rating(String game, String player, int rating, Date playedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = playedOn;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int points) {
        this.rating = points;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date playedOn) {
        this.ratedOn = playedOn;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ident=" + ident +
                ", game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }
}
