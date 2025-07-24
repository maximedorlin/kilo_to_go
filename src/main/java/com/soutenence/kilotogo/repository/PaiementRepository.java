package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}