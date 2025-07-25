package com.soutenence.kilotogo.dto;

import com.soutenence.kilotogo.entity.enums.PaiementMethode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {
    private Long transactionId;
    private PaiementMethode methode;
    private Double montant;
    private String phoneNumber; // Pour MOMO/OM
    private String email; // Pour PayPal
}