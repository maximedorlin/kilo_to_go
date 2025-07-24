package com.soutenence.kilotogo.controller;

import com.soutenence.kilotogo.entity.User;
import com.soutenence.kilotogo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping public List<User> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping public User createUser(@RequestBody User user) { return userService.createUser(user); }
    @PutMapping("/{id}") public User updateUser(@PathVariable Long id, @RequestBody User userDetails) { return userService.updateUser(id, userDetails); }
    @DeleteMapping("/{id}") public void deleteUser(@PathVariable Long id) { userService.deleteUser(id); }
    @PatchMapping("/{id}") public User partialUpdateUser(@PathVariable Long id, @RequestBody User userUpdates) { return userService.partialUpdateUser(id, userUpdates); }
}