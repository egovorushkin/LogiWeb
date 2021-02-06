package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.UserDao;
import com.egovorushkin.logiweb.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUserName(String userName) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u " +
                "LEFT JOIN FETCH u.roles WHERE u.userName=:userName", User.class)
                .setParameter("userName", userName);
        User user = null;
        try {
            user = q.getSingleResult();
        } catch (NoResultException ignored) {
            //
        }

        return user;
    }

    @Override
    public void save(User theUser) {
        entityManager.persist(theUser);
    }

}
