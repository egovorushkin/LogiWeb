package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.RoleDao;
import com.egovorushkin.logiweb.dao.api.UserDao;
import com.egovorushkin.logiweb.dto.UserDto;
import com.egovorushkin.logiweb.entities.Role;
import com.egovorushkin.logiweb.entities.User;
import com.egovorushkin.logiweb.services.api.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    @Transactional
    public void save(UserDto userDto) {
        User user = new User();

        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRoles(Collections.singletonList(roleDao.findRoleByName(
                "ROLE_DRIVER")));
        userDao.save(user);
    }

    @Override
    @Transactional
    public void saveAdmin(UserDto userDto) {
        User user = new User();

        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRoles(Collections.singletonList(roleDao.findRoleByName(
                "ROLE_ADMIN")));
        userDao.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails
                .User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority>
                                mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority
                        (role.getName())).collect(Collectors.toList());
    }

}
