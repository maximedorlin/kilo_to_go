package com.soutenence.kilotogo.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Annonce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private User utilisateur;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private AnnonceCategorie categorie;

    @Column(nullable = false)
    private Double prix;

    private String devise = "XOF";
    private Double poids;
    private String dimensions;

    @Column(name = "ville_depart", nullable = false)
    private String villeDepart;

    @Column(name = "ville_arrivee", nullable = false)
    private String villeArrivee;

    @Column(name = "date_depart_prevu")
    private LocalDate dateDepartPrevu;

    @Column(name = "date_arrivee_prevu")
    private LocalDate dateArriveePrevu;

    @Enumerated(EnumType.STRING)
    private AnnonceStatus statut = AnnonceStatus.disponible;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @OneToOne(mappedBy = "annonce", cascade = CascadeType.ALL)
    private Transaction transaction;
}

enum AnnonceCategorie {
    electronique, vetements, meubles, livres, autres
}

enum AnnonceStatus {
    disponible, reserve, expedie, livre, annule
}
