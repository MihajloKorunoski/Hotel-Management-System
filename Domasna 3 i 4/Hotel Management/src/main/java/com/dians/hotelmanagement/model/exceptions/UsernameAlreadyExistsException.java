package com.dians.hotelmanagement.model.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String email) {
        super("Username with email " + email + " already exists");
    }
}
