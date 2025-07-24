package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.Paiement;
import com.soutenence.kilotogo.repository.PaiementRepository;
import com.soutenence.kilotogo.service.PaiementService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementServiceImpl implements PaiementService {
    private final PaiementRepository paiementRepository;

    public PaiementServiceImpl(PaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    @Override
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Optional<Paiement> getPaiementById(Long id) {
        return paiementRepository.findById(id);
    }

    @Override
    public Paiement createPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement updatePaiement(Long id, Paiement paiementDetails) {
        Paiement existingPaiement = paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement not found"));
        BeanUtils.copyProperties(paiementDetails, existingPaiement, "id", "transaction");
        return paiementRepository.save(existingPaiement);
    }

    @Override
    public void deletePaiement(Long id) {
        paiementRepository.deleteById(id);
    }

    @Override
    public Paiement partialUpdatePaiement(Long id, Paiement paiementUpdates) {
        return paiementRepository.findById(id).map(existingPaiement -> {
            if (paiementUpdates.getMethode() != null) existingPaiement.setMethode(paiementUpdates.getMethode());
            if (paiementUpdates.getMontant() != null) existingPaiement.setMontant(paiementUpdates.getMontant());
            if (paiementUpdates.getReferencePaiement() != null) existingPaiement.setReferencePaiement(paiementUpdates.getReferencePaiement());
            if (paiementUpdates.getStatut() != null) existingPaiement.setStatut(paiementUpdates.getStatut());
            if (paiementUpdates.getDatePaiement() != null) existingPaiement.setDatePaiement(paiementUpdates.getDatePaiement());
            if (paiementUpdates.getInformationsSupplementaires() != null) existingPaiement.setInformationsSupplementaires(paiementUpdates.getInformationsSupplementaires());
            return paiementRepository.save(existingPaiement);
        }).orElseThrow(() -> new RuntimeException("Paiement not found"));
    }
}
