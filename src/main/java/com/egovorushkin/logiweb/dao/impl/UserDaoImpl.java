package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.UserDao;
import com.egovorushkin.logiweb.entities.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUserName(String userName) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username", User.class)
                .setParameter("username", userName);

        User user = null;

        try {
            user = q.getSingleResult();
        } catch (Exception e) {
            user = null;
        }

        LOGGER.info("findByUserName");

        return user;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
}
