package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.Transaction;
import com.soutenence.kilotogo.repository.TransactionRepository;
import com.soutenence.kilotogo.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
}
