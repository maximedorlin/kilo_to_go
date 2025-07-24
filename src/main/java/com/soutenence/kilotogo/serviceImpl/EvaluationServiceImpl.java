package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.Evaluation;
import com.soutenence.kilotogo.repository.EvaluationRepository;
import com.soutenence.kilotogo.service.EvaluationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepository evaluationRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Override
    public Optional<Evaluation> getEvaluationById(Long id) {
        return evaluationRepository.findById(id);
    }

    @Override
    public Evaluation createEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation updateEvaluation(Long id, Evaluation evaluationDetails) {
        Evaluation existingEvaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));
        BeanUtils.copyProperties(evaluationDetails, existingEvaluation, "id", "evaluateur", "evalue", "transaction", "dateCreation");
        return evaluationRepository.save(existingEvaluation);
    }

    @Override
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }

    @Override
    public Evaluation partialUpdateEvaluation(Long id, Evaluation evaluationUpdates) {
        return evaluationRepository.findById(id).map(existingEvaluation -> {
            if (evaluationUpdates.getNote() != null) existingEvaluation.setNote(evaluationUpdates.getNote());
            if (evaluationUpdates.getCommentaire() != null) existingEvaluation.setCommentaire(evaluationUpdates.getCommentaire());
            return evaluationRepository.save(existingEvaluation);
        }).orElseThrow(() -> new RuntimeException("Evaluation not found"));
    }
}
