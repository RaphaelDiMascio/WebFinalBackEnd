package com.dauphine.web_final_back_end.services.impl;

import com.dauphine.web_final_back_end.exceptions.BadRequestException;
import com.dauphine.web_final_back_end.exceptions.UserNotFoundByIdException;
import com.dauphine.web_final_back_end.models.User;
import com.dauphine.web_final_back_end.repositories.UserRepository;
import com.dauphine.web_final_back_end.services.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public User create(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Le nom d'utilisateur '" + user.getUsername() + "' est déjà pris.");
        }
        return repository.save(user);
    }
    
    @Override
    public User getById(UUID id) throws UserNotFoundByIdException {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundByIdException("User introuvable"));
    }

    @Override
    public User login(String username, String password) throws UserNotFoundByIdException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundByIdException("Nom d'utilisateur introuvable"));
        if (!user.getPassword().equals(password)) {
            throw new BadRequestException("Mot de passe incorrect");
        }
        return user;
    }
}
