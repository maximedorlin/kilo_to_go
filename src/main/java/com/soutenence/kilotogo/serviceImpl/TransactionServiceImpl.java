package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.Evaluation;
import com.soutenence.kilotogo.entity.Paiement;
import com.soutenence.kilotogo.entity.SuiviColis;
import com.soutenence.kilotogo.entity.Transaction;
import com.soutenence.kilotogo.repository.EvaluationRepository;
import com.soutenence.kilotogo.repository.PaiementRepository;
import com.soutenence.kilotogo.repository.SuiviColisRepository;
import com.soutenence.kilotogo.repository.TransactionRepository;
import com.soutenence.kilotogo.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final SuiviColisRepository suiviColisRepository;

    private final EvaluationRepository evaluationRepository;

    private final PaiementRepository paiementRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  SuiviColisRepository suiviColisRepository,
                                  EvaluationRepository evaluationRepository,
                                  PaiementRepository paiementRepository) {
        this.transactionRepository = transactionRepository;
        this.suiviColisRepository = suiviColisRepository;
        this.evaluationRepository = evaluationRepository;
        this.paiementRepository = paiementRepository;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        BeanUtils.copyProperties(transactionDetails, existingTransaction, "id", "annonce", "acheteur", "vendeur", "dateCreation");
        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Transaction partialUpdateTransaction(Long id, Transaction transactionUpdates) {
        return transactionRepository.findById(id).map(existingTransaction -> {
            if (transactionUpdates.getMontant() != null) existingTransaction.setMontant(transactionUpdates.getMontant());
            if (transactionUpdates.getDevise() != null) existingTransaction.setDevise(transactionUpdates.getDevise());
            if (transactionUpdates.getStatut() != null) existingTransaction.setStatut(transactionUpdates.getStatut());
            return transactionRepository.save(existingTransaction);
        }).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public Paiement createPaiementForTransaction(Long transactionId, Paiement paiement) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        paiement.setTransaction(transaction);
        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> getPaiementsForTransaction(Long transactionId) {
        return paiementRepository.findByTransactionId(transactionId);
    }

    @Override
    public SuiviColis createSuiviColisForTransaction(Long transactionId, SuiviColis suiviColis) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        suiviColis.setTransaction(transaction);
        return suiviColisRepository.save(suiviColis);
    }

    @Override
    public Optional<SuiviColis> getSuiviColisForTransaction(Long transactionId) {
        return suiviColisRepository.findByTransactionId(transactionId);
    }

    @Override
    public Evaluation createEvaluationForTransaction(Long transactionId, Evaluation evaluation) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        evaluation.setTransaction(transaction);
        return evaluationRepository.save(evaluation);
    }

    @Override
    public List<Evaluation> getEvaluationsForTransaction(Long transactionId) {
        return evaluationRepository.findByTransactionId(transactionId);
    }

    @Override
    public void deleteAllPaiementsForTransaction(Long transactionId) {
        paiementRepository.deleteByTransactionId(transactionId);
    }

    @Override
    public void deleteSuiviColisForTransaction(Long transactionId) {
        suiviColisRepository.deleteByTransactionId(transactionId);
    }

    @Override
    public void deleteAllEvaluationsForTransaction(Long transactionId) {
        evaluationRepository.deleteByTransactionId(transactionId);
    }

    @Override
    public SuiviColis updateSuiviColisForTransaction(Long transactionId, SuiviColis suiviColisDetails) {
        SuiviColis existingSuiviColis = suiviColisRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("SuiviColis not found for transaction"));

        BeanUtils.copyProperties(suiviColisDetails, existingSuiviColis, "id", "transaction");
        return suiviColisRepository.save(existingSuiviColis);
    }
}
