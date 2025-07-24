package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.SuiviColis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuiviColisRepository extends JpaRepository<SuiviColis, Long> {
    Optional<SuiviColis> findByTransactionId(Long transactionId);
    void deleteByTransactionId(Long transactionId);
}
