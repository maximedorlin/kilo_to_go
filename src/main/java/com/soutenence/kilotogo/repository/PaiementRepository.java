package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByTransactionId(Long transactionId);
    void deleteByTransactionId(Long transactionId);
    Optional<Paiement> findByReferencePaiement(String referencePaiement);
}