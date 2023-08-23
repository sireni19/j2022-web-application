package main.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static final String USER_DB = "root";
    private static final String USER_PWD = "";
    private static final String USER_URL = "jdbc:mysql://localhost:3308/web_users_db";


    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(USER_URL, USER_DB, USER_PWD);
            System.out.println(con + " is open");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }


    public static void release(Connection con) {
        try {
            if (con != null) {
                con.close();
                System.out.println(con + " is closed");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Connection conn = getConnection();
        release(conn);
    }

}
