package com.soutenence.kilotogo.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SuiviColis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuiviColis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    private ColisStatus statut;

    private String localisation;

    @Column(name = "date_mise_a_jour")
    private LocalDateTime dateMiseAJour = LocalDateTime.now();

    private String commentaire;
}

enum ColisStatus {
    preparation, en_transit, en_livraison, livre, probleme
}
