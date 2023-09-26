package com.coderiders.userservice.exceptions;

public class BookNotFoundException extends UserServiceException{

    public BookNotFoundException() {
        super();
    }

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(Throwable cause) {
        super(cause);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
