package com.dians.hotelmanagement.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid User Credentials");
    }
}
