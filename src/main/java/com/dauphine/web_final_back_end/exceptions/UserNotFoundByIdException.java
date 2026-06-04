package com.dauphine.web_final_back_end.exceptions;

public class UserNotFoundByIdException extends Exception {
    public UserNotFoundByIdException(String message) {
        super(message);
    }
}
