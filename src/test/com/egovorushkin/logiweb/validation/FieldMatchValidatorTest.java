package com.egovorushkin.logiweb.validation;

import com.egovorushkin.logiweb.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidatorContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FieldMatchValidatorTest {

    public static final String firstFieldName ="admin";
    public static final String secondFieldName = "admin";
    UserDto userDto = new UserDto();

    private FieldMatchValidator validator = new FieldMatchValidator();
    ConstraintValidatorContext context;

    @BeforeEach
    public void init() {
        context = mock(ConstraintValidatorContext.class);
        userDto.setPassword("admin");
        userDto.setMatchingPassword("admin");
    }

        // TODO: need to finish the test
//    @Test
//    @DisplayName("Test is valid success")
//    void testIsValidSuccess() {
//        when(new BeanWrapperImpl(userDto).getPropertyValue(firstFieldName)).thenReturn("admin");
//        when(new BeanWrapperImpl(userDto).getPropertyValue(firstFieldName)).thenReturn("admin");
//
//        boolean expectedResult = true;
//        boolean actualResult = validator.isValid(userDto, context);
//        Assertions.assertEquals(expectedResult, actualResult);
//    }
}
