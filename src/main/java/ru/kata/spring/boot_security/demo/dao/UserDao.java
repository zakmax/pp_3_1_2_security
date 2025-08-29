package ru.kata.spring.boot_security.demo.dao;





import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;


public interface UserDao {
    List<User> index();
    User show(int id);
    void save(User user);
    void update(int id, User updatedUser);
    void delete(int id);
    List<User> findByUsername(String username);
}