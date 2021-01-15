package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.RoleDao;
import com.egovorushkin.logiweb.entities.Role;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Repository
public class RoleDaoImpl implements RoleDao {

    private static final Logger LOGGER = Logger.getLogger(RoleDaoImpl.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role findRoleByName(String theRoleName) {
        TypedQuery<Role> q = entityManager.createQuery("SELECT r FROM Role r WHERE r.name=:theRoleName", Role.class).setParameter("theRoleName", theRoleName);


        Role theRole = null;

        try {
            theRole = q.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }

        LOGGER.info("findRoleByName");

        return theRole;
    }
}
