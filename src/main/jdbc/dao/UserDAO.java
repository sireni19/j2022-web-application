package main.jdbc.dao;

import main.jdbc.model.User;

public interface UserDAO {
    User getUserByEmail(String email);
    User getUserById(int id);
    boolean createUser(User user);

    boolean activate(String email);
}
