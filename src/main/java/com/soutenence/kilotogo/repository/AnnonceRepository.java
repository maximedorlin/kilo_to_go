package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByUtilisateurId(Long userId);

    void deleteByUtilisateurId(Long userId);
}
