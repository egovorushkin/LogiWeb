package com.egovorushkin.logiweb.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Represent main abstract data access object
 */
public abstract class AbstractDao {

    @PersistenceContext
    public EntityManager entityManager;
}
