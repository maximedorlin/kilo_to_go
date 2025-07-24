package com.soutenence.kilotogo.service;

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
}