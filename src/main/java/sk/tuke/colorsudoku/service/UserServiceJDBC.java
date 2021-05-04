package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceJDBC implements UserService{
    public static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgr3s1";
    public static final String SELECT = "SELECT username, passwords, show_tips, registered_on FROM users WHERE username = ?";
    public static final String DELETE = "DELETE FROM users";
    public static final String INSERT = "INSERT INTO users (username, passwords, show_tips, registered_on) VALUES (?, ?, ?, ?)";
    public static final String SELECT1 = "SELECT username FROM users";

    @Override
    public void addUser(Users users) throws UserException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, users.getUsername());
            statement.setString(2, users.getPassword());
            statement.setBoolean(3, users.isShow_tips());
            statement.setTimestamp(4, new Timestamp(users.getRegistered_on().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException("Problem inserting user", e);
        }
    }

    @Override
    public String getPassword(String username) throws UserException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)
        ) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                List<Users> users = new ArrayList<>();
                String userName1;
                users.add(new Users(rs.getString(1),rs.getString(2),rs.getBoolean(3),rs.getTimestamp(4)));


                userName1 = users.get(0).getPassword();

                return userName1;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting user", e);
        }
    }

    @Override
    public void reset() throws UserException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting users", e);
        }
    }

    @Override
    public List<String> getUsernames() throws UserException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT1)
        ) {
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                List<String> usernames = new ArrayList<>();
                usernames.add(rs.getString(1));

                return usernames;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting user", e);
        }
    }


}
