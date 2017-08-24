package com.laba.booklibrary.service.connection;

/**
 * Wrapper for connection pool exceptions
 */
public class ConnectionPoolException extends Exception {

    public ConnectionPoolException(Exception ex){
        super(ex);
    }

}
