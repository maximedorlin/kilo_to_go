package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}