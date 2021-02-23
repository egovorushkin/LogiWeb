package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.validation.FieldMatch;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
/**
 * Represent a user
 * implements {@link Serializable}
 * with validator {@link FieldMatch}
 */
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message
                = "The password fields must match")})
public class UserDto implements Serializable {

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String lastName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String userName;

    private String formRole;

    private CityDto currentCity;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getFormRole() {
        return formRole;
    }

    public void setFormRole(String formRole) {
        this.formRole = formRole;
    }

    public CityDto getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(CityDto currentCity) {
        this.currentCity = currentCity;
    }
}
