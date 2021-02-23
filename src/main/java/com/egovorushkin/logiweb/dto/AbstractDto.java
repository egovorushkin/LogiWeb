package com.egovorushkin.logiweb.dto;

import java.io.Serializable;

/**
 * Represent a abstract object
 *
 */
public abstract class AbstractDto implements Serializable {

   private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
