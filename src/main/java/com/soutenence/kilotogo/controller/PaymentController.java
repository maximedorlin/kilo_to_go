package com.soutenence.kilotogo.controller;


import com.soutenence.kilotogo.dto.PaymentRequestDTO;
import com.soutenence.kilotogo.entity.Paiement;
import com.soutenence.kilotogo.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/paiements")
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;
    private final String momoApiSecret;
    private final String omApiSecret;
    private final String paypalApiSecret;

    // Injectez les secrets via le constructeur
    public PaymentController(
        PaymentService paymentService,
        @Value("${momo.api-secret}") String momoApiSecret,
        @Value("${om.api-secret}") String omApiSecret,
        @Value("${paypal.secret}") String paypalApiSecret
    ) {
        this.paymentService = paymentService;
        this.momoApiSecret = momoApiSecret;
        this.omApiSecret = omApiSecret;
        this.paypalApiSecret = paypalApiSecret;
    }

    // Endpoints CRUD existants...

    @PostMapping("/initiate")
    public ResponseEntity<?> initiatePayment(@RequestBody PaymentRequestDTO request) {
        try {
            Paiement paiement = paymentService.initierPaiement(request);
            return ResponseEntity.ok(paiement);
        } catch (Exception e) {
            log.error("Erreur d'initiation de paiement", e);
            return ResponseEntity.badRequest().body(
                Map.of("error", "Échec de l'initialisation", 
                       "details", e.getMessage())
            );
        }
    }

    @PostMapping("/webhook/{provider}")
    public ResponseEntity<String> handleWebhook(
            @PathVariable String provider,
            @RequestHeader(value = "X-Signature", required = false) String signature,
            @RequestBody Map<String, Object> payload) {
        
        log.info("Webhook reçu de {}: {}", provider, payload);
        
        try {
            if (!isValidSignature(provider, payload, signature)) {
                log.warn("Signature invalide pour le fournisseur: {}", provider);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Signature invalide");
            }
            
            paymentService.processWebhook(provider, payload);
            return ResponseEntity.ok("Webhook traité avec succès");
        } catch (Exception e) {
            log.error("Erreur de traitement webhook", e);
            return ResponseEntity.internalServerError().body("Erreur de traitement: " + e.getMessage());
        }
    }

  private boolean isValidSignature(String provider, Map<String, Object> payload, String signature) {
        try {
            switch (provider.toLowerCase()) {
                case "momo":
                    return validateMomoSignature(payload, signature);
                case "om":
                    return validateOMSignature(payload, signature);
                case "paypal":
                    return validatePayPalSignature(payload, signature);
                default:
                    log.warn("Validation de signature non implémentée pour {}", provider);
                    return true;
            }
        } catch (Exception e) {
            log.error("Erreur de validation de signature", e);
            return false;
        }
    }

    private boolean validateMomoSignature(Map<String, Object> payload, String signature) {
        if (signature == null) return false;
        
        // Conversion sécurisée en Strings
        String reference = Objects.toString(payload.get("reference"), "");
        String nonce = Objects.toString(payload.get("nonce"), "");
        String timestamp = Objects.toString(payload.get("timestamp"), "");
        
        String data = reference + nonce + timestamp;
        String calculatedSignature = paymentService.calculateHMAC(data, momoApiSecret);
        
        return calculatedSignature.equals(signature);
    }

    private boolean validateOMSignature(Map<String, Object> payload, String signature) {
        if (signature == null) return false;
        
        String reference = Objects.toString(payload.get("reference"), "");
        String timestamp = Objects.toString(payload.get("timestamp"), "");
        String msisdn = Objects.toString(payload.get("msisdn"), "");
        
        String data = reference + timestamp + msisdn;
        String calculatedSignature = paymentService.calculateHMAC(data, omApiSecret);
        
        return calculatedSignature.equals(signature);
    }

    private boolean validatePayPalSignature(Map<String, Object> payload, String signature) {
        if (signature == null) return false;
        
        String reference = Objects.toString(payload.get("reference"), "");
        String timestamp = Objects.toString(payload.get("timestamp"), "");
        String payp = Objects.toString(payload.get("payp"), "");
        
        String data = reference + timestamp + payp;
        String calculatedSignature = paymentService.calculateHMAC(data, paypalApiSecret);
        
        return calculatedSignature.equals(signature);
    }
}