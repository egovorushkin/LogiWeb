package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User user);
}
