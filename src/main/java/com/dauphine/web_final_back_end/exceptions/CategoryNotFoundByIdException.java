package com.dauphine.web_final_back_end.exceptions;

public class CategoryNotFoundByIdException extends Exception {
    public CategoryNotFoundByIdException(String message) {
        super(message);
    }
}
