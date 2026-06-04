package com.dauphine.web_final_back_end.repositories;

import com.dauphine.web_final_back_end.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, UUID> {
    // Utile pour la future connexion ou création de compte
    Optional<User> findByUsername(String username);
}