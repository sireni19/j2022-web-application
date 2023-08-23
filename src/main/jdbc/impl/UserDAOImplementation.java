package main.jdbc.impl;

import main.jdbc.DBUtils;
import main.jdbc.dao.UserDAO;
import main.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImplementation implements UserDAO {
    @Override
    public User getUserByEmail(String email) {
        User user = null;
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM web_users_db.users where email=?");
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                System.out.println(String.format("User email '%s'  was found", email));
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPwd(resultSet.getString("password"));
                return user;
            } else {
                System.out.println(String.format("User email '%s'  wasn`t found", email));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public boolean createUser(User user) {

        try (Connection conn = DBUtils.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO  web_users_db.users (name, email, password) VALUES (?, ?, ?)");
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPwd());
            pstmt.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean activate(String email) {
        try (Connection connection = DBUtils.getConnection()) {
            String sql = "UPDATE users SET is_active = 'Y' WHERE users.email=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
