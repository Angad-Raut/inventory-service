package com.microservice.inventoryservice.exception;

public class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }
}
