package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account register(Account account){
        Connection connection = ConnectionUtil.getConnection();
        Account newAccount = new Account();
        try{
            String sql = "INSERT INTO account(username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            newAccount.setPassword(rs.getString("password"));
            newAccount.setUsername(rs.getString("username"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newAccount;
    }

    public Account login(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();
        Account account = new Account();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            account.setAccount_id(rs.getInt("account_id"));
            account.setPassword(rs.getString("password"));
            account.setUsername(rs.getString("username"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
}
