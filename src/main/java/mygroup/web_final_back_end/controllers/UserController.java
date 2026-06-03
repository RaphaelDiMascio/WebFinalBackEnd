package mygroup.web_final_back_end.controllers;
import io.swagger.v3.oas.annotations.tags.Tag;
import mygroup.web_final_back_end.exceptions.UserNotFoundByIdException;
import mygroup.web_final_back_end.models.User;
import mygroup.web_final_back_end.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) throws UserNotFoundByIdException {
        return ResponseEntity.ok(userService.login(user.getUsername(), user.getPassword()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) throws UserNotFoundByIdException {
        return ResponseEntity.ok(userService.getById(id));
    }
}
