package dao;

import entity.User;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {
//  public List<User> getLikedUsers() {
//    return users.stream()
//        .filter(user -> Boolean.TRUE.equals(user.getLiked()))
//        .collect(Collectors.toList());
//  }

  private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("id");
    String name = resultSet.getString("name");
    String photo = resultSet.getString("photo");
    String liked = resultSet.getString("liked");
    Boolean userLiked = null;

    if (liked != null) {
      userLiked = "1".equals(liked);
    }

    return new User(id, name, photo, userLiked);
  }

  public User getNotLikedUser() {
    try (
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement()
    ) {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE liked IS NULL LIMIT 1");

      if (resultSet.next()) {
        return getUserFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public void saveLike(String name, boolean like) {
    try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET liked=? WHERE name=?")
    ) {
      statement.setBoolean(1, like);
      statement.setString(2, name);

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<User> getLikedUsers() {
    List<User> users = new ArrayList<>();

    try (
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement()
    ) {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE liked IS TRUE");

      while (resultSet.next()) {
        users.add(getUserFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users;
  }

  public User getUserByLoginAndPassword(String name, String password) {
    try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name=? AND password=?")
    ) {
      statement.setString(1, name);
      statement.setString(2, password);

      statement.execute();

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return getUserFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

    public User getUserByLogin(String name) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name=?")
        ) {
            statement.setString(1, name);

            statement.execute();

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
