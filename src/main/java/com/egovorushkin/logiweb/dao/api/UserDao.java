package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.User;

/**
 * This interface represent some methods to operate on a {@link User}
 * (retrieve from and save to database)
 */
public interface UserDao {

    User findByUserName(String userName);

    void save(User user);

    void deleteUser(String userName);
}
