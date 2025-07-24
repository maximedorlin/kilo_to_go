package com.soutenence.kilotogo.controller;

import com.soutenence.kilotogo.entity.Evaluation;
import com.soutenence.kilotogo.entity.Paiement;
import com.soutenence.kilotogo.entity.SuiviColis;
import com.soutenence.kilotogo.entity.Transaction;
import com.soutenence.kilotogo.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionDetails) {
        return transactionService.updateTransaction(id, transactionDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

    @PatchMapping("/{id}")
    public Transaction partialUpdateTransaction(@PathVariable Long id, @RequestBody Transaction transactionUpdates) {
        return transactionService.partialUpdateTransaction(id, transactionUpdates);
    }


    @PostMapping("/{transactionId}/paiements")
    public Paiement createPaiementForTransaction(
            @PathVariable Long transactionId,
            @RequestBody Paiement paiement) {
        return transactionService.createPaiementForTransaction(transactionId, paiement);
    }

    @GetMapping("/{transactionId}/paiements")
    public List<Paiement> getPaiementsForTransaction(@PathVariable Long transactionId) {
        return transactionService.getPaiementsForTransaction(transactionId);
    }

    @PostMapping("/{transactionId}/suiviColis")
    public SuiviColis createSuiviColisForTransaction(
            @PathVariable Long transactionId,
            @RequestBody SuiviColis suiviColis) {
        return transactionService.createSuiviColisForTransaction(transactionId, suiviColis);
    }

    @GetMapping("/{transactionId}/suiviColis")
    public ResponseEntity<SuiviColis> getSuiviColisForTransaction(@PathVariable Long transactionId) {
        Optional<SuiviColis> suiviColis = transactionService.getSuiviColisForTransaction(transactionId);
        return suiviColis.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{transactionId}/evaluations")
    public Evaluation createEvaluationForTransaction(
            @PathVariable Long transactionId,
            @RequestBody Evaluation evaluation) {
        return transactionService.createEvaluationForTransaction(transactionId, evaluation);
    }

    @GetMapping("/{transactionId}/evaluations")
    public List<Evaluation> getEvaluationsForTransaction(@PathVariable Long transactionId) {
        return transactionService.getEvaluationsForTransaction(transactionId);
    }

    @DeleteMapping("/{transactionId}/paiements")
    public void deleteAllPaiementsForTransaction(@PathVariable Long transactionId) {
        transactionService.deleteAllPaiementsForTransaction(transactionId);
    }

    @DeleteMapping("/{transactionId}/suiviColis")
    public void deleteSuiviColisForTransaction(@PathVariable Long transactionId) {
        transactionService.deleteSuiviColisForTransaction(transactionId);
    }

    @DeleteMapping("/{transactionId}/evaluations")
    public void deleteAllEvaluationsForTransaction(@PathVariable Long transactionId) {
        transactionService.deleteAllEvaluationsForTransaction(transactionId);
    }

    @PutMapping("/{transactionId}/suiviColis")
    public SuiviColis updateSuiviColisForTransaction(
            @PathVariable Long transactionId,
            @RequestBody SuiviColis suiviColisDetails) {
        return transactionService.updateSuiviColisForTransaction(transactionId, suiviColisDetails);
    }
}
