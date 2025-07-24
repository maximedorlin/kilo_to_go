package com.soutenence.kilotogo.service;

import com.soutenence.kilotogo.entity.Evaluation;
import java.util.List;
import java.util.Optional;

public interface EvaluationService {
    List<Evaluation> getAllEvaluations();
    Optional<Evaluation> getEvaluationById(Long id);
    Evaluation createEvaluation(Evaluation evaluation);
    Evaluation updateEvaluation(Long id, Evaluation evaluationDetails);
    void deleteEvaluation(Long id);
    Evaluation partialUpdateEvaluation(Long id, Evaluation evaluationUpdates);
}
