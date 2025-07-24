package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.*;
import com.soutenence.kilotogo.repository.AnnonceRepository;
import com.soutenence.kilotogo.repository.UserRepository;
import com.soutenence.kilotogo.repository.EvaluationRepository;
import com.soutenence.kilotogo.repository.TransactionRepository;
import com.soutenence.kilotogo.repository.NotificationRepository;

import com.soutenence.kilotogo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final AnnonceRepository annonceRepository;

    private final EvaluationRepository evaluationRepository;

    private final TransactionRepository transactionRepository;
    private final NotificationRepository notificationRepository;

    public UserServiceImpl(UserRepository userRepository,
                           AnnonceRepository annonceRepository,
                           EvaluationRepository evaluationRepository,
                           TransactionRepository transactionRepository,
                           NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.annonceRepository = annonceRepository;
        this.evaluationRepository = evaluationRepository;
        this.transactionRepository = transactionRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override public List<User> getAllUsers() { return userRepository.findAll(); }
    @Override public Optional<User> getUserById(Long id) { return userRepository.findById(id); }
    @Override public User createUser(User user) { return userRepository.save(user); }

    @Override
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        BeanUtils.copyProperties(userDetails, existingUser, "id");
        return userRepository.save(existingUser);
    }

    @Override public void deleteUser(Long id) { userRepository.deleteById(id); }

    @Override
    public User partialUpdateUser(Long id, User userUpdates) {
        return userRepository.findById(id).map(existingUser -> {
            if (userUpdates.getNom() != null) existingUser.setNom(userUpdates.getNom());
            if (userUpdates.getPrenom() != null) existingUser.setPrenom(userUpdates.getPrenom());
            if (userUpdates.getEmail() != null) existingUser.setEmail(userUpdates.getEmail());
            // Ajouter tous les autres champs...
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<Annonce> getUserAnnonces(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAnnonces();
    }

    @Override
    public Annonce createAnnonceForUser(Long userId, Annonce annonce) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        annonce.setUtilisateur(user);
        return annonceRepository.save(annonce);
    }

    @Override
    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepository.findByAcheteurIdOrVendeurId(userId, userId);
    }

    @Override
    public List<Evaluation> getUserEvaluations(Long userId) {
        return evaluationRepository.findByEvalueId(userId);
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUtilisateurId(userId);
    }

    @Override
    public void deleteAllAnnoncesForUser(Long userId) {
        annonceRepository.deleteByUtilisateurId(userId);
    }

    @Override
    public void deleteAllTransactionsForUser(Long userId) {
        transactionRepository.deleteByAcheteurIdOrVendeurId(userId, userId);
    }

    @Override
    public void deleteTransactionForAnnonce(Long annonceId) {
        transactionRepository.deleteByAnnonceId(annonceId);
    }
}