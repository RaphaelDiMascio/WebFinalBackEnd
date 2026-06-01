package mygroup.web_final_back_end.services;

import mygroup.web_final_back_end.models.User;
import mygroup.web_final_back_end.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public User create(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Le nom d'utilisateur '" + user.getUsername() + "' est déjà pris.");
        }
        return repository.save(user);
    }
    public User getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User introuvable"));
    }
}