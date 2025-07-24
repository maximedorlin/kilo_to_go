package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.User;
import com.soutenence.kilotogo.repository.UserRepository;
import com.soutenence.kilotogo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}