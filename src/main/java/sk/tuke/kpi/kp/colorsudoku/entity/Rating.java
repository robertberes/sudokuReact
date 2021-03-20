package sk.tuke.kpi.kp.colorsudoku.entity;


import java.util.Date;

public class Rating {
    private String game;

    private String player;

    private int rating;

    private Date ratedOn;

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

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", playedOn=" + ratedOn +
                '}';
    }
}
