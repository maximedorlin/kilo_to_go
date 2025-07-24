package com.soutenence.kilotogo.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String telephone;

    private String adresse;
    private String ville;
    private String pays;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "date_inscription")
    private LocalDateTime inscriptionDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private UserStatus statut = UserStatus.actif;

    @Column(name = "score_reputation")
    private Double reputationScore = 5.0;

    @Column(name = "photo_profil")
    private String profilePhoto;

    private Boolean verifie = false;

    @Column(name = "derniere_connexion")
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Annonce> annonces;

    @OneToMany(mappedBy = "acheteur", cascade = CascadeType.ALL)
    private List<Transaction> achats;

    @OneToMany(mappedBy = "vendeur", cascade = CascadeType.ALL)
    private List<Transaction> ventes;
}

enum UserStatus {
    actif, inactif, suspendu
}