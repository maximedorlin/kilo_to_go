package com.soutenence.kilotogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.soutenence.kilotogo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
