package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.RoleDao;
import com.egovorushkin.logiweb.entities.Role;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Represent a repository
 * and implements methods to operate for {@link Role}
 * extends {@link AbstractDao}
 * implements {@link RoleDao}
 * annotated {@link Repository}
 *
 */
@Repository
public class RoleDaoImpl extends AbstractDao implements RoleDao {

    private static final Logger LOGGER =
            Logger.getLogger(RoleDaoImpl.class.getName());

    @Override
    public Role findRoleByName(String theRoleName) {
        TypedQuery<Role> q = entityManager
                .createQuery("SELECT r FROM Role r WHERE r.name=:name",
                        Role.class)
                .setParameter("name", theRoleName);

        Role theRole = null;

        try {
            theRole = q.getSingleResult();
        } catch (Exception ignored) {
            LOGGER.info("Role with name " + theRoleName + " not found");
        }

        return theRole;
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager
                .createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }
}
