package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Role;

public interface RoleDao {

    Role findRoleByName(String theRoleName);
}
