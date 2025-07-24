package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.Annonce;
import com.soutenence.kilotogo.entity.Transaction;
import com.soutenence.kilotogo.repository.AnnonceRepository;
import com.soutenence.kilotogo.repository.TransactionRepository;
import com.soutenence.kilotogo.service.AnnonceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnonceServiceImpl implements AnnonceService {
    private final AnnonceRepository annonceRepository;

    private final TransactionRepository transactionRepository;

    public AnnonceServiceImpl(AnnonceRepository annonceRepository, TransactionRepository transactionRepository) {
        this.annonceRepository = annonceRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @Override
    public Optional<Annonce> getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    @Override
    public Annonce createAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    @Override
    public Annonce updateAnnonce(Long id, Annonce annonceDetails) {
        Annonce existingAnnonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce not found"));
        BeanUtils.copyProperties(annonceDetails, existingAnnonce, "id", "utilisateur", "dateCreation");
        return annonceRepository.save(existingAnnonce);
    }

    @Override
    public void deleteAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }

    @Override
    public Annonce partialUpdateAnnonce(Long id, Annonce annonceUpdates) {
        return annonceRepository.findById(id).map(existingAnnonce -> {
            if (annonceUpdates.getTitre() != null)
                existingAnnonce.setTitre(annonceUpdates.getTitre());
            if (annonceUpdates.getDescription() != null)
                existingAnnonce.setDescription(annonceUpdates.getDescription());
            if (annonceUpdates.getCategorie() != null)
                existingAnnonce.setCategorie(annonceUpdates.getCategorie());
            if (annonceUpdates.getPrix() != null)
                existingAnnonce.setPrix(annonceUpdates.getPrix());
            if (annonceUpdates.getDevise() != null)
                existingAnnonce.setDevise(annonceUpdates.getDevise());
            if (annonceUpdates.getPoids() != null)
                existingAnnonce.setPoids(annonceUpdates.getPoids());
            if (annonceUpdates.getDimensions() != null)
                existingAnnonce.setDimensions(annonceUpdates.getDimensions());
            if (annonceUpdates.getVilleDepart() != null)
                existingAnnonce.setVilleDepart(annonceUpdates.getVilleDepart());
            if (annonceUpdates.getVilleArrivee() != null)
                existingAnnonce.setVilleArrivee(annonceUpdates.getVilleArrivee());
            if (annonceUpdates.getDateDepartPrevu() != null)
                existingAnnonce.setDateDepartPrevu(annonceUpdates.getDateDepartPrevu());
            if (annonceUpdates.getDateArriveePrevu() != null)
                existingAnnonce.setDateArriveePrevu(annonceUpdates.getDateArriveePrevu());
            if (annonceUpdates.getStatut() != null)
                existingAnnonce.setStatut(annonceUpdates.getStatut());
            return annonceRepository.save(existingAnnonce);
        }).orElseThrow(() -> new RuntimeException("Annonce not found"));
    }

    @Override
    public Transaction createTransactionForAnnonce(Long annonceId, Transaction transaction) {
        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new RuntimeException("Annonce not found"));

        transaction.setAnnonce(annonce);
        transaction.setVendeur(annonce.getUtilisateur());

        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> getTransactionForAnnonce(Long annonceId) {
        return transactionRepository.findByAnnonceId(annonceId);
    }

    @Override
    public void deleteTransactionForAnnonce(Long annonceId) {
        transactionRepository.deleteByAnnonceId(annonceId);
    }

}