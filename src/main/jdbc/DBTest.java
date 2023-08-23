package main.jdbc;

import main.jdbc.dao.UserDAO;
import main.jdbc.impl.UserDAOImplementation;
import main.jdbc.model.User;

public class DBTest {
    public static void main(String[] args) {
        UserDAO dao=new UserDAOImplementation();
        User u=dao.getUserByEmail("John@gmail.com");
        System.out.println(u);
    }

}
