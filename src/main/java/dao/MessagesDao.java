package dao;

import entity.Message;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessagesDao {

  public List<Message> getAllMessages(String fromName, String toName){
    List<Message> chat = new ArrayList<>();
    try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE messages.from=? AND messages.to=? ORDER BY id ASC")
    ){
      statement.setString(1, fromName);
      statement.setString(2, toName);
      ResultSet resultSet = statement.executeQuery();
      while(resultSet.next()){
        Message message = new Message();
        message.setId(resultSet.getInt("id"));
        message.setFrom(resultSet.getString("from"));
        message.setTo(resultSet.getString("to"));
        message.setMessage(resultSet.getString("text"));
        chat.add(message);
      }


  } catch (SQLException e){
    e.printStackTrace();
  }
    return chat;
  }

  public void saveMessage(){

  }



}
