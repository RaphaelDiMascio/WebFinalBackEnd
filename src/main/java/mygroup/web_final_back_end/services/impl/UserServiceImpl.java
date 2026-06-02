package mygroup.web_final_back_end.services.impl;

import mygroup.web_final_back_end.models.User;
import mygroup.web_final_back_end.repositories.UserRepository;
import mygroup.web_final_back_end.services.UserService;
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
            throw new RuntimeException("Le nom d'utilisateur '" + user.getUsername() + "' est déjà pris.");
        }
        return repository.save(user);
    }
    
    @Override
    public User getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User introuvable"));
    }
}
