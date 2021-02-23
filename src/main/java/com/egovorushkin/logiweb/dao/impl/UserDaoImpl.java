package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.UserDao;
import com.egovorushkin.logiweb.entities.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Represent a repository
 * and implements methods to operate for {@link User}
 * extends {@link AbstractDao}
 * implements {@link UserDao}
 * annotated {@link Repository}
 *
 */
@Repository
public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final Logger LOGGER =
            Logger.getLogger(UserDaoImpl.class.getName());

    @Override
    public User findByUserName(String userName) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u " +
                "LEFT JOIN FETCH u.roles " +
                "WHERE u.userName=:userName", User.class)
                .setParameter("userName", userName);
        User user = null;
        try {
            user = q.getSingleResult();
        } catch (NoResultException ignored) {
            LOGGER.info("User with username " + userName + " not found");
        }
        return user;
    }

    @Override
    public void save(User theUser) {
        entityManager.persist(theUser);
    }

}
