package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repository.UserRepo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {
        return userRepo.findAll();
    }

    @Override
    public boolean addUser(UserDao userDao) {
        if (!isNameUnique(userDao)) {
            return false;
        }
        User user = fromForm(userDao);
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean updateUser(UserDao userDao) {
        if (getUserById(userDao.getId()).getName().equals(userDao.getName()) || isNameUnique(userDao)) {
            User user = fromForm(userDao);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public User getUserByName(String name) throws IllegalStateException {
        return userRepo.findByName(name).orElseThrow(() -> new IllegalStateException("User not find by name"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getUserByName(s);
    }

    private void setRoles(User user, UserDao userDao) {
        user.setRoles(Arrays.stream(userDao.getRoles())
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet()));
    }

    private boolean isNameUnique(UserDao userDao) {
        return !userRepo.findByName(userDao.getName()).isPresent();
    }

    private User fromForm(UserDao userDao) {
        User user = new User(userDao);
        setRoles(user, userDao);
        user.setPassword(passwordEncoder.encode(userDao.getPassword()));
        return user;
    }
}