package com.soutenence.kilotogo.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.soutenence.kilotogo.entity.enums.PaiementMethode;
import com.soutenence.kilotogo.entity.enums.PaiementStatus;

@Entity
@Table(name = "Paiement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    private PaiementMethode methode;

    @Column(nullable = false)
    private Double montant;

    @Column(name = "reference_paiement", unique = true)
    private String referencePaiement;

    @Enumerated(EnumType.STRING)
    private PaiementStatus statut = PaiementStatus.en_attente;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(name = "informations_supplementaires", columnDefinition = "TEXT")
    private String informationsSupplementaires;
}