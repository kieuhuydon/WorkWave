package vn.kieudon.workwave.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.kieudon.workwave.domain.User;
import vn.kieudon.workwave.service.UserService;
import vn.kieudon.workwave.service.error.IdInvalidException;

import java.util.List;


@RestController

public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        String hashPass = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPass);
        User userSave = this.userService.handleSaveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User fetchUser = this.userService.fetchUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetchUser);
    }

    

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable long id) throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("id không quá 1500");
        }
        this.userService.deleteUserById(id);
        return ResponseEntity.ok("ericUser");
        // return ResponseEntity.status(HttpStatus.OK).body("ericUser");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable long id, @RequestBody User user) {
        User updateUser = this.userService.handleUpdateUser(id, user);
        return ResponseEntity.ok(updateUser);
    }
}
