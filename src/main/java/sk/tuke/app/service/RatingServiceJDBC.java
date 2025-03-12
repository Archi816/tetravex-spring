package sk.tuke.app.service;

import sk.tuke.app.entity.Rating;
import java.sql.*;
import java.util.Date;

public class RatingServiceJDBC implements RatingService {
    private static final String URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qm_xcklv";

    private static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";
    private static final String SELECT = "SELECT rating FROM rating WHERE game = ? AND player = ?";
    private static final String UPDATE = "UPDATE rating SET rating = ?, ratedOn = ? WHERE game = ? AND player = ?";
    private static final String AVERAGE = "SELECT AVG(rating) FROM rating WHERE game = ?";
    private static final String DELETE = "DELETE FROM rating";

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(SELECT);
             PreparedStatement insertStatement = connection.prepareStatement(INSERT);
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE)) {

            selectStatement.setString(1, rating.getGame());
            selectStatement.setString(2, rating.getPlayer());
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                updateStatement.setInt(1, rating.getRating());
                updateStatement.setTimestamp(2, new Timestamp(rating.getRatedOn().getTime()));
                updateStatement.setString(3, rating.getGame());
                updateStatement.setString(4, rating.getPlayer());
                updateStatement.executeUpdate();
            } else {
                insertStatement.setString(1, rating.getGame());
                insertStatement.setString(2, rating.getPlayer());
                insertStatement.setInt(3, rating.getRating());
                insertStatement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RatingException("Problem setting rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)) {

            statement.setString(1, game);
            statement.setString(2, player);
            ResultSet rs = statement.executeQuery();

            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RatingException("Problem retrieving rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(AVERAGE)) {

            statement.setString(1, game);
            ResultSet rs = statement.executeQuery();

            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RatingException("Problem retrieving average rating", e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem resetting ratings", e);
        }
    }
}
