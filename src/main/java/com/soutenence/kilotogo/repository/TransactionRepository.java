package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAcheteurIdOrVendeurId(Long acheteurId, Long vendeurId);
    Optional<Transaction> findByAnnonceId(Long annonceId);
    void deleteByAnnonceId(Long annonceId);

    void deleteByAcheteurIdOrVendeurId(Long userId, Long userId1);
}
