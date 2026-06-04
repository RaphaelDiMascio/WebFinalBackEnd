package com.dauphine.web_final_back_end.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.dauphine.web_final_back_end.exceptions.UserNotFoundByIdException;
import com.dauphine.web_final_back_end.models.User;
import com.dauphine.web_final_back_end.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Register user", description = "Register/create a new user account")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate a user with username and password")
    public ResponseEntity<User> loginUser(@RequestBody User user) throws UserNotFoundByIdException {
        return ResponseEntity.ok(userService.login(user.getUsername(), user.getPassword()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user's details by their unique ID")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) throws UserNotFoundByIdException {
        return ResponseEntity.ok(userService.getById(id));
    }
}
