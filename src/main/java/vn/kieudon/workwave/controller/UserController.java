package vn.kieudon.workwave.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.kieudon.workwave.domain.User;
import vn.kieudon.workwave.domain.dto.ResultPagination;
import vn.kieudon.workwave.service.UserService;
import vn.kieudon.workwave.util.error.IdInvalidException;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<ResultPagination> getAllUser(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {

        String scurrent = currentOptional.isPresent() ? currentOptional.get() : "";
        String spageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";

        int current = Integer.parseInt(scurrent);
        int pageSize = Integer.parseInt(spageSize);

        Pageable pageable = PageRequest.of(current - 1, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(pageable));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User fetchUser = this.userService.fetchUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetchUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("id không quá 1500");
        }
        this.userService.deleteUserById(id);
        return ResponseEntity.ok("ericUser");
        // return ResponseEntity.status(HttpStatus.OK).body("ericUser");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        User updateUser = this.userService.handleUpdateUser(id, user);
        return ResponseEntity.ok(updateUser);
    }
}
