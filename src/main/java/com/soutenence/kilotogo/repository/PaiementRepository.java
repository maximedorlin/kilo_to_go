package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByTransactionId(Long transactionId);
    void deleteByTransactionId(Long transactionId);
}