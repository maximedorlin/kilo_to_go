package com.soutenence.kilotogo.controller;

import com.soutenence.kilotogo.entity.Annonce;
import com.soutenence.kilotogo.entity.Transaction;
import com.soutenence.kilotogo.service.AnnonceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {
    private final AnnonceService annonceService;

    public AnnonceController(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

    @GetMapping
    public List<Annonce> getAllAnnonces() {
        return annonceService.getAllAnnonces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getAnnonceById(@PathVariable Long id) {
        Optional<Annonce> annonce = annonceService.getAnnonceById(id);
        return annonce.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Annonce createAnnonce(@RequestBody Annonce annonce) {
        return annonceService.createAnnonce(annonce);
    }

    @PutMapping("/{id}")
    public Annonce updateAnnonce(@PathVariable Long id, @RequestBody Annonce annonceDetails) {
        return annonceService.updateAnnonce(id, annonceDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteAnnonce(@PathVariable Long id) {
        annonceService.deleteAnnonce(id);
    }

    @PatchMapping("/{id}")
    public Annonce partialUpdateAnnonce(@PathVariable Long id, @RequestBody Annonce annonceUpdates) {
        return annonceService.partialUpdateAnnonce(id, annonceUpdates);
    }

    @PostMapping("/{annonceId}/transaction")
    public Transaction createTransactionForAnnonce(
            @PathVariable Long annonceId,
            @RequestBody Transaction transaction) {
        return annonceService.createTransactionForAnnonce(annonceId, transaction);
    }

    @GetMapping("/{annonceId}/transaction")
    public ResponseEntity<Transaction> getTransactionForAnnonce(@PathVariable Long annonceId) {
        Optional<Transaction> transaction = annonceService.getTransactionForAnnonce(annonceId);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{annonceId}/transaction")
    public void deleteTransactionForAnnonce(@PathVariable Long annonceId) {
        annonceService.deleteTransactionForAnnonce(annonceId);
    }
    
}
