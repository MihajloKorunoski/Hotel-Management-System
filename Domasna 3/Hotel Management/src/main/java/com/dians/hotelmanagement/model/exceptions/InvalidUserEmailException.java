package com.dians.hotelmanagement.model.exceptions;

public class InvalidUserEmailException extends RuntimeException{
    public InvalidUserEmailException()
    {
        super("User with email  does not exist!");
    }
}
