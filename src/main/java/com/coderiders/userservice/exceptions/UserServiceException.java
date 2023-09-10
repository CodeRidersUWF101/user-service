package com.coderiders.userservice.exceptions;



public class UserServiceException extends RuntimeException{
    public UserServiceException() {
        super();
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
