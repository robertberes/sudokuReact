package sk.tuke.kpi.kp.colorsudoku.service;


import sk.tuke.kpi.kp.colorsudoku.entity.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgr3s1";
    public static final String SELECT = "SELECT game, player, rating, ratedOn FROM rating WHERE game = ?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE rating SET rating = ? WHERE player = ?";
    public static final String SELECT1 = "SELECT game, player, rating, ratedOn FROM rating WHERE game = ? AND player = ?";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT);
             PreparedStatement statement2 = connection.prepareStatement(UPDATE);
             PreparedStatement statement1 = connection.prepareStatement(SELECT)
        ) {
            statement1.setString(1,rating.getGame());
            try (ResultSet rs = statement1.executeQuery()){

                while (rs.next()){
                    if (rs.getString(2).equals(rating.getPlayer())){
                        statement2.setInt(1,rating.getRating());
                        statement2.setString(2, rating.getPlayer());

                        statement2.executeUpdate();
                        return;
                    }
                }
            }
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RatingException("Problem inserting rating",e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                int sumOfRatings = 0;
                int averageRatings = 0;
                int iterator = 0;
                while (rs.next()) {
                    sumOfRatings += rs.getInt(3);
                    iterator++;
                }
                if (sumOfRatings!=0)
                    averageRatings = sumOfRatings/iterator;
                return averageRatings;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem showing average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT1)
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                List<Rating> ratings = new ArrayList<>();
                int playerRating;
                ratings.add(new Rating(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getTimestamp(4)));


                playerRating = ratings.get(0).getRating();

                return playerRating;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }

    }
}
