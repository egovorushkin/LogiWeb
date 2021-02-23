package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Role;

import java.util.List;

/**
 * This interface represent some methods to operate on a {@link Role}
 * (retrieve from and save to database)
 */
public interface RoleDao {

    Role findRoleByName(String theRoleName);

    List<Role> getAllRoles();

}
