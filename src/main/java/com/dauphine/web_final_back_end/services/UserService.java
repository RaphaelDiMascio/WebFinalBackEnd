package com.dauphine.web_final_back_end.services;

import com.dauphine.web_final_back_end.exceptions.UserNotFoundByIdException;
import com.dauphine.web_final_back_end.models.User;

import java.util.UUID;

public interface UserService {
    User create(User user);
    User getById(UUID id) throws UserNotFoundByIdException;
    User login(String username, String password) throws UserNotFoundByIdException;
}