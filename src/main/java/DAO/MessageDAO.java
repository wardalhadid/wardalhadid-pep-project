package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message newMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            return this.getCreatedMessage(message);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAllMessages() {
       Connection connection = ConnectionUtil.getConnection();
       List<Message> messages = new ArrayList<>();

       try {
        String sql = "SELECT * FROM message;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            messages.add(message);
        }
       } catch(SQLException e) {
        e.printStackTrace();
       }

       return messages;
    }

    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = new Message();
        try {
        String sql = "SELECT * FROM message WHERE message_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            message.setMessage_id(rs.getInt("message_id"));
        message.setMessage_text(rs.getString("message_text"));
        message.setPosted_by(rs.getInt("posted_by"));
        message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
        }
    } catch(SQLException e){
        e.printStackTrace();
       }
       return message;
    }

    public Message getCreatedMessage( Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
        String sql = "SELECT * FROM message WHERE posted_by = ? AND time_posted_epoch = ? AND message_text = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, message.getPosted_by());
        preparedStatement.setLong(2, message.getTime_posted_epoch());
        preparedStatement.setString(3, message.getMessage_text());
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
        Message msg = new Message(rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
        msg.setMessage_id(rs.getInt("message_id"));
        return msg;
        }
        } catch(SQLException e){
            e.printStackTrace();
       }
        return null;
    }

    public Message deleteMessage (int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            Message messageToBeDeleted = this.getMessageById(id);
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return messageToBeDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Message updateMessage (int id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return this.getMessageById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 

    public List<Message> getAllMessagesFromAccountId ( int id ) {
        List<Message> allMessages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                message.setMessage_id(rs.getInt("message_id"));
                allMessages.add(message);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allMessages;
    }
}
