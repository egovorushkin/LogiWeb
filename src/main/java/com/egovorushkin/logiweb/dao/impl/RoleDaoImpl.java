package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.RoleDao;
import com.egovorushkin.logiweb.entities.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

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
            //
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
