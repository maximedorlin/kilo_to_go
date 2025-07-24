package com.soutenence.kilotogo.service;

import java.util.List;
import java.util.Optional;

import com.soutenence.kilotogo.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    User partialUpdateUser(Long id, User userUpdates);
}