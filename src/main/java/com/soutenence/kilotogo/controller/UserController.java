package com.soutenence.kilotogo.controller;

import com.soutenence.kilotogo.entity.*;
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

    @GetMapping("/{userId}/annonces")
    public List<Annonce> getUserAnnonces(@PathVariable Long userId) {
        return userService.getUserAnnonces(userId);
    }

    @PostMapping("/{userId}/annonces")
    public Annonce createAnnonceForUser(@PathVariable Long userId, @RequestBody Annonce annonce) {
        return userService.createAnnonceForUser(userId, annonce);
    }

    @GetMapping("/{userId}/transactions")
    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
        return userService.getUserTransactions(userId);
    }

    @GetMapping("/{userId}/evaluations")
    public List<Evaluation> getUserEvaluations(@PathVariable Long userId) {
        return userService.getUserEvaluations(userId);
    }

    @GetMapping("/{userId}/notifications")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return userService.getUserNotifications(userId);
    }
    @DeleteMapping("/{userId}/annonces")
    public void deleteAllAnnoncesForUser(@PathVariable Long userId) {
        userService.deleteAllAnnoncesForUser(userId);
    }

    @DeleteMapping("/{userId}/transactions")
    public void deleteAllTransactionsForUser(@PathVariable Long userId) {
        userService.deleteAllTransactionsForUser(userId);
    }
}