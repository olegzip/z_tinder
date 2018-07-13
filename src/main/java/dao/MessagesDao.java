package dao;

import entity.Message;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessagesDao {

  public List<Message> getAllMessages(String sender, String reciever){
    List<Message> chat = new ArrayList<>();
    try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages_no WHERE " +
                    "messages_no.sender=? AND messages_no.reciever=? " +
                    " UNION SELECT * FROM messages_no WHERE " +
                    "messages_no.sender=? AND messages_no.reciever=? " +
                    " ORDER BY id ASC")
    ){
      statement.setString(1, sender);
      statement.setString(2, reciever);
      statement.setString(3, reciever);
      statement.setString(4, sender);
      ResultSet resultSet = statement.executeQuery();
      while(resultSet.next()){
        Message message = new Message();
        message.setId(resultSet.getInt("id"));
        message.setSender(resultSet.getString("sender"));
        message.setReciever(resultSet.getString("reciever"));
        message.setText(resultSet.getString("message"));
        chat.add(message);
      }
  } catch (SQLException e){
    e.printStackTrace();
  }
    return chat;
  }

  public void saveMessage(String sender, String reciever, String message){
    try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO messages_no (sender, reciever, message) VALUES (?, ?, ?)")
    ){
      statement.setString(1, sender);
      statement.setString(2, reciever);
      statement.setString(3, message);
      statement.executeUpdate();
    } catch (SQLException e){
      e.printStackTrace();
    }
  }
}
