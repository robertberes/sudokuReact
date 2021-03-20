package sk.tuke.kpi.kp.colorsudoku.service;


import sk.tuke.kpi.kp.colorsudoku.entity.Rating;

import java.sql.*;





public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgr3s1";
    public static final String SELECT = "SELECT game, player, rating, ratedOn FROM rating WHERE game = ?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";


    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
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
             PreparedStatement statement = connection.prepareStatement(SELECT);
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
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet rs = statement.executeQuery()) {
                int playerRating = 0;
                playerRating = rs.getInt(3);
                return playerRating;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }

    }
}
