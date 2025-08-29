package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.User;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public List<User> index() {
        return userDao.index();
    }
    @Transactional
    @Override
    public User show(int id) {
        return userDao.show(id);
    }
    @Transactional
    @Override
    public  void save(User user) {
        userDao.save(user);
    }

    @Transactional
    @Override
    public void update(int id, User updatedUser) {
        User userToBeUpdated = show(id);
        userToBeUpdated.setUsername(updatedUser.getUsername());

    }

    @Transactional
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }
}