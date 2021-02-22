package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.dao.api.UserDao;
import com.egovorushkin.logiweb.dao.api.RoleDao;
import com.egovorushkin.logiweb.dto.UserDto;
import com.egovorushkin.logiweb.entities.User;
import com.egovorushkin.logiweb.services.api.UserService;
import com.egovorushkin.logiweb.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long USER_ONE_ID = 1L;
    private static final String USER_ONE_USERNAME = "john";
    private static final String USER_ONE_PASSWORD = "john";
    private static final String USER_ONE_FIRSTNAME = "John";
    private static final String USER_ONE_LASTNAME = "Johnson";

    UserService userService;

    @Mock
    private  UserDao userDao;
    private final User userOne = new User();
    ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        RoleDao roleDao = Mockito.mock(RoleDao.class);
        BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        modelMapper = new ModelMapper();
        userService = new UserServiceImpl(userDao, roleDao, passwordEncoder);

        userOne.setId(USER_ONE_ID);
        userOne.setUserName(USER_ONE_USERNAME);
        userOne.setLastName(USER_ONE_PASSWORD);
        userOne.setFirstName(USER_ONE_FIRSTNAME);
        userOne.setLastName(USER_ONE_LASTNAME);
    }

    @Test
    @DisplayName("Test findByUserName success")
    void testFindByUserNameSuccess() {
        when(userDao.findByUserName(USER_ONE_USERNAME)).thenReturn(userOne);

        User userTwo = userService.findByUserName(USER_ONE_USERNAME);

        Assertions.assertEquals(userOne, userTwo);
    }


    @Test
    @DisplayName("Test saveUser success")
    void saveUserSuccess() {
        userService.save(modelMapper.map(userOne, UserDto.class));
        verify(userDao, times(1))
                .save(any(User.class));
    }

    @Test
    @DisplayName("Test saveAdmin success")
    void saveAdminSuccess() {
        userService.saveAdmin(modelMapper.map(userOne, UserDto.class));
        verify(userDao, times(1))
                .save(any(User.class));
    }

}
