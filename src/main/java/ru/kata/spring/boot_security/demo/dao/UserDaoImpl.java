package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
          entityManager.persist(user);
    }
    @Override
    public List<User> index() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User show(int id) {
        return entityManager.find(User.class, id);
        }

    @Override
    public void update(int id, User updatedUser){
        entityManager.merge(updatedUser);
        }
        @Override
        public  void delete(int id) {

        entityManager.remove(show(id));
        }

    @Override
    public List<User> findByUsername(String username) {

        return entityManager.createQuery("select u from User u join fetch u.roles where u.username =:username").setParameter("username", username).getResultList();
    }
}
