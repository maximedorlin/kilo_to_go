package com.soutenence.kilotogo.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private User utilisateur;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    private String lien;

    private Boolean lue = false;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();
}

enum NotificationType {
    message, transaction, paiement, annonce, evaluation
}
