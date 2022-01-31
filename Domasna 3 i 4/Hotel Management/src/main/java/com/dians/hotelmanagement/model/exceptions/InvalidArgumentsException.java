package com.dians.hotelmanagement.model.exceptions;

public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException() {
        super("Please fill out all fields");
    }
}
