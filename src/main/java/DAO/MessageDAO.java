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
        Message createdMessage = new Message();
        try{
            String sql = "INSERT INTO message VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            ResultSet rs = preparedStatement.executeQuery();
            createdMessage = message;
            createdMessage.setMessage_id(rs.getInt("message_id"));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return createdMessage;
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
        message.setMessage_id(rs.getInt("message_id"));
        message.setMessage_text(rs.getString("message_text"));
        message.setPosted_by(rs.getInt("posted_by"));
        message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
    } catch(SQLException e){
        e.printStackTrace();
       }
       return message;
    }

}
