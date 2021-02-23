package com.egovorushkin.logiweb.exceptions;

/**
 * This exception is thrown when the requested logiweb entity (driver, truck,
 * order, cargo or user) is already exist.
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
