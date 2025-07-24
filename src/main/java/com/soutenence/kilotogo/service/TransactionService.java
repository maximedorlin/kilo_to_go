package com.soutenence.kilotogo.service;

import com.soutenence.kilotogo.entity.Evaluation;
import com.soutenence.kilotogo.entity.Paiement;
import com.soutenence.kilotogo.entity.SuiviColis;
import com.soutenence.kilotogo.entity.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    Optional<Transaction> getTransactionById(Long id);
    Transaction createTransaction(Transaction transaction);
    Transaction updateTransaction(Long id, Transaction transactionDetails);
    void deleteTransaction(Long id);
    Transaction partialUpdateTransaction(Long id, Transaction transactionUpdates);

    Paiement createPaiementForTransaction(Long transactionId, Paiement paiement);
    List<Paiement> getPaiementsForTransaction(Long transactionId);
    SuiviColis createSuiviColisForTransaction(Long transactionId, SuiviColis suiviColis);
    Optional<SuiviColis> getSuiviColisForTransaction(Long transactionId);
    Evaluation createEvaluationForTransaction(Long transactionId, Evaluation evaluation);
    List<Evaluation> getEvaluationsForTransaction(Long transactionId);

    void deleteAllPaiementsForTransaction(Long transactionId);

    void deleteSuiviColisForTransaction(Long transactionId);

    void deleteAllEvaluationsForTransaction(Long transactionId);

    SuiviColis updateSuiviColisForTransaction(Long transactionId, SuiviColis suiviColisDetails);
}