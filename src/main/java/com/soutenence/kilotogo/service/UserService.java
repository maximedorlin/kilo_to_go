package com.soutenence.kilotogo.service;

import java.util.List;
import java.util.Optional;

import com.soutenence.kilotogo.entity.*;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    User partialUpdateUser(Long id, User userUpdates);

    List<Annonce> getUserAnnonces(Long userId);
    Annonce createAnnonceForUser(Long userId, Annonce annonce);
    List<Transaction> getUserTransactions(Long userId);
    List<Evaluation> getUserEvaluations(Long userId);
    List<Notification> getUserNotifications(Long userId);

    void deleteAllAnnoncesForUser(Long userId);

    void deleteAllTransactionsForUser(Long userId);

    void deleteTransactionForAnnonce(Long annonceId);
}