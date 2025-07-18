package vn.kieudon.workwave.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.kieudon.workwave.domain.User;
import vn.kieudon.workwave.service.UserService;

import java.util.Optional;

@RestController

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("users")
    public ResponseEntity<User>  createUser (@RequestBody User user) {
        User userSave = this.userService.handleSaveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable long id) {
        return this.userService.findUserById(id).orElse(null); // hoặc dùng ResponseEntity
    }

    @DeleteMapping("users/{id}")
    public void deleteUserById(@PathVariable long id) {
        this.userService.deleteUserById(id);
    }

    @PutMapping("users/{id}")
    public User updateUserById(@PathVariable long id, @RequestBody User user) {
        Optional<User> existingUser = this.userService.findUserById(id);
        if (existingUser.isPresent()) {
            User currentUser = existingUser.get();
            if (user.getName() != null) currentUser.setName(user.getName());
            if (user.getEmail() != null) currentUser.setEmail(user.getEmail());
            if (user.getPassword() != null) currentUser.setPassword(user.getPassword());
            this.userService.handleSaveUser(currentUser);
            return currentUser;
        }
        return null;
    }
}
