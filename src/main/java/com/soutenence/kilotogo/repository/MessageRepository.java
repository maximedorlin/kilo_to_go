package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
