package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
}
