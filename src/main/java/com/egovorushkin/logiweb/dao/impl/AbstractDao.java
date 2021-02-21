package com.egovorushkin.logiweb.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDao {

    @PersistenceContext
    public EntityManager entityManager;
}
