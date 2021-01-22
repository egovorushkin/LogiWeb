package com.egovorushkin.logiweb.exceptions;

/**
 * This exception is thrown when the requested logiweb entity (driver, truck,
 * order, cargo or user) isn't found from the database.
 */

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String s) {
        super(s);
    }
}
