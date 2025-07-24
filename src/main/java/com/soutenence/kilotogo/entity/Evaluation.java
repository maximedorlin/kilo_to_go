package com.soutenence.kilotogo.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Evaluation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluateur_id", nullable = false)
    private User evaluateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evalue_id", nullable = false)
    private User evalue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Column(nullable = false)
    private Integer note;

    private String commentaire;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();
}