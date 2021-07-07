package sk.tuke.colorsudoku.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery( name = "Rating.getAverageRating",
                query = "SELECT avg(s.rating) from Rating s where s.game=:game and s.rating > 0"),
        @NamedQuery( name = "Rating.resetRating",
                query = "DELETE FROM Rating "),
        @NamedQuery( name = "Rating.getRating",
                query = "SELECT r FROM Rating r WHERE r.game=:game and r.player =: player"),
        @NamedQuery( name = "Rating.selectRating",
                query = "SELECT r FROM Rating r WHERE r.game=:game and r.player =: player"),
        @NamedQuery( name = "Rating.delRating",
                query = "delete FROM Rating r WHERE r.game=:game and r.player =: player")})

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
