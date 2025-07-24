package com.soutenence.kilotogo.service;

import com.soutenence.kilotogo.entity.Annonce;
import com.soutenence.kilotogo.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface AnnonceService {
    List<Annonce> getAllAnnonces();
    Optional<Annonce> getAnnonceById(Long id);
    Annonce createAnnonce(Annonce annonce);
    Annonce updateAnnonce(Long id, Annonce annonceDetails);
    void deleteAnnonce(Long id);
    Annonce partialUpdateAnnonce(Long id, Annonce annonceUpdates);

    Transaction createTransactionForAnnonce(Long annonceId, Transaction transaction);
    Optional<Transaction> getTransactionForAnnonce(Long annonceId);
    void deleteTransactionForAnnonce(Long annonceId);
}
