package sk.tuke.colorsudoku.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@NamedQueries({
        @NamedQuery( name = "Score.getTopScores",
                query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points DESC"),
        @NamedQuery( name = "Score.getTopScores1",
                query = "SELECT s FROM Score s WHERE s.game=:game AND s.difficulty=:difficulty ORDER BY s.points DESC"),
        @NamedQuery( name = "Score.reset",
                query = "DELETE FROM Score")})
public class Score implements Serializable {
    @Id
    @GeneratedValue
    private int ident;

    private String game;

    private String player;

    private int points;

    private Date played_at;

    private String difficulty;

    public Score(){}

    public Score(String game, String player, int points, Date played_at, String difficulty) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.played_at = played_at;
        this.difficulty = difficulty;
    }
    public Score(String game, String player, int points, Date played_at){
        this.game = game;
        this.player = player;
        this.points = points;
        this.played_at = played_at;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedOn() {
        return played_at;
    }

    public void setPlayedOn(Date played_at) {
        this.played_at = played_at;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    @Override
    public String toString() {
        return "Score{" +
                "ident=" + ident +
                ", game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", played_at=" + played_at +
                ", difficulty=" + difficulty + '\'' +
                '}';
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
