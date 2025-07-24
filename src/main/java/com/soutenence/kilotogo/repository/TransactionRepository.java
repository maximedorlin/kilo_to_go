package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
