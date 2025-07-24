package com.soutenence.kilotogo.service;

import com.soutenence.kilotogo.entity.Paiement;
import java.util.List;
import java.util.Optional;

public interface PaiementService {
    List<Paiement> getAllPaiements();
    Optional<Paiement> getPaiementById(Long id);
    Paiement createPaiement(Paiement paiement);
    Paiement updatePaiement(Long id, Paiement paiementDetails);
    void deletePaiement(Long id);
    Paiement partialUpdatePaiement(Long id, Paiement paiementUpdates);
}
