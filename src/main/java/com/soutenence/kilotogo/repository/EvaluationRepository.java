package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByEvalueId(Long evalueId);
    List<Evaluation> findByTransactionId(Long transactionId);
    void deleteByTransactionId(Long transactionId);
}