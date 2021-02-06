package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.UserDto;
import com.egovorushkin.logiweb.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void save(UserDto userDto);
}
