package sk.tuke.app.service;

import sk.tuke.app.entity.Comment;
import sk.tuke.app.entity.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qm_xcklv";

    private static final String INSERT = "INSERT INTO comment (game, player, comment, commentedOn) VALUES (?, ?, ?, ?)";
    private static final String SELECT = "SELECT game, player, comment, commentedOn FROM comment WHERE game = ? ORDER BY commentedOn DESC LIMIT 10";
    private static final String DELETE = "DELETE FROM comment";


    @Override
    public void addComment(Comment comment) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, comment.getGame());
            statement.setString(2, comment.getPlayer());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Problem while adding comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(SELECT)
        ) {
            statement.setString(1, game);
            ResultSet resultSet = statement.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                comments.add(new Comment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getTimestamp(4)));
            }
        } catch(SQLException e) {
            throw new CommentException("Problem while getting comments", e);
        }
        return List.of();
    }

    @Override
    public void reset() throws CommentException {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e){
            throw new CommentException("Problem while resetting comments", e);
        }
    }
}
