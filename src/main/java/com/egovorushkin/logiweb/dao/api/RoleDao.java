package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Role;

import java.util.List;

public interface RoleDao {

    Role findRoleByName(String theRoleName);

    List<Role> getAllRoles();

}
