package com.app.theravenwithoutauth.exception;

/**
 * Exception thrown to indicate that a customer already exists in the system.
 */
public class CustomerAlreadyExistsException extends Exception{
    public CustomerAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CustomerAlreadyExistsException(String msg) {
        super(msg);
    }
}
