package com.soutenence.kilotogo.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soutenence.kilotogo.dto.PaymentRequestDTO;
import com.soutenence.kilotogo.entity.*;
import com.soutenence.kilotogo.entity.enums.*;
import com.soutenence.kilotogo.exception.PaymentException;
import com.soutenence.kilotogo.repository.PaiementRepository;
import com.soutenence.kilotogo.repository.TransactionRepository;
import com.soutenence.kilotogo.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaiementRepository paiementRepository;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${paypal.client-id}") private String paypalClientId;
    @Value("${paypal.secret}") private String paypalSecret;
    @Value("${paypal.api-url}") private String paypalApiUrl;
    @Value("${momo.api-url}") private String momoApiUrl;
    @Value("${momo.api-key}") private String momoApiKey;
    @Value("${momo.api-secret}") private String momoApiSecret;
    @Value("${om.api-url}") private String omApiUrl;
    @Value("${om.api-key}") private String omApiKey;
    @Value("${om.api-secret}") private String omApiSecret;

    public PaymentServiceImpl(PaiementRepository paiementRepository, 
                              TransactionRepository transactionRepository,
                              RestTemplate restTemplate) {
        this.paiementRepository = paiementRepository;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public Paiement initierPaiement(PaymentRequestDTO request) {
        try {
            Transaction transaction = transactionRepository.findById(request.getTransactionId())
                    .orElseThrow(() -> new PaymentException("Transaction non trouvée"));

            Paiement paiement = new Paiement();
            paiement.setTransaction(transaction);
            paiement.setMethode(request.getMethode());
            paiement.setMontant(request.getMontant());
            paiement.setStatut(PaiementStatus.en_attente);

            Map<String, String> infos = new HashMap<>();
            String reference = generateTransactionId(request.getMethode());
            paiement.setReferencePaiement(reference);

            switch (request.getMethode()) {
                case MOMO:
                    infos.put("phone", request.getPhoneNumber());
                    initierPaiementMOMO(request, reference);
                    break;
                case OM:
                    infos.put("phone", request.getPhoneNumber());
                    initierPaiementOM(request, reference);
                    break;
                case PayPal:
                    infos.put("email", request.getEmail());
                    String approvalUrl = initierPaiementPayPal(request, reference);
                    infos.put("approval_url", approvalUrl);
                    break;
            }

            paiement.setInformationsSupplementaires(objectMapper.writeValueAsString(infos));
            return paiementRepository.save(paiement);
            
        } catch (Exception e) {
            log.error("Échec de l'initialisation du paiement", e);
            throw new PaymentException("Échec de l'initialisation: " + e.getMessage());
        }
    }

    private void initierPaiementMOMO(PaymentRequestDTO request, String reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + momoApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Générer un nonce pour la sécurité
        String nonce = UUID.randomUUID().toString();
        String timestamp = String.valueOf(System.currentTimeMillis());
        
        // Créer la signature
        String data = reference + nonce + timestamp;
        String signature = calculateHMAC(data, momoApiSecret);

        Map<String, Object> body = new HashMap<>();
        body.put("amount", request.getMontant());
        body.put("phone", request.getPhoneNumber());
        body.put("reference", reference);
        body.put("currency", "XAF");
        body.put("nonce", nonce);
        body.put("timestamp", timestamp);
        body.put("signature", signature);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(momoApiUrl + "/payments", entity, Map.class);
        
        if (!response.getStatusCode().is2xxSuccessful() || !"SUCCESS".equals(response.getBody().get("status"))) {
            throw new PaymentException("Échec de la requête MOMO: " + response.getBody());
        }
    }

    private void initierPaiementOM(PaymentRequestDTO request, String reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + omApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        String timestamp = String.valueOf(System.currentTimeMillis());
        String data = reference + timestamp + request.getPhoneNumber();
        String signature = calculateHMAC(data, omApiSecret);

        Map<String, Object> body = new HashMap<>();
        body.put("amount", request.getMontant());
        body.put("msisdn", request.getPhoneNumber());
        body.put("reference", reference);
        body.put("timestamp", timestamp);
        body.put("signature", signature);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(omApiUrl + "/transaction", entity, Map.class);
        
        if (!response.getStatusCode().is2xxSuccessful() || !"200".equals(response.getBody().get("code"))) {
            throw new PaymentException("Échec de la requête OM: " + response.getBody());
        }
    }

    private String initierPaiementPayPal(PaymentRequestDTO request, String reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(paypalClientId, paypalSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("PayPal-Request-Id", reference);

        Map<String, Object> amount = new HashMap<>();
        amount.put("currency_code", "XAF");
        amount.put("value", request.getMontant());

        Map<String, Object> purchaseUnit = new HashMap<>();
        purchaseUnit.put("amount", amount);
        purchaseUnit.put("reference_id", reference);

        Map<String, Object> body = new HashMap<>();
        body.put("intent", "CAPTURE");
        body.put("purchase_units", Collections.singletonList(purchaseUnit));
        body.put("application_context", Map.of(
            "return_url", "https://votre-domaine.com/payment/success",
            "cancel_url", "https://votre-domaine.com/payment/cancel"
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
            paypalApiUrl + "/v2/checkout/orders", 
            entity, 
            Map.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> links = (List<Map<String, Object>>) responseBody.get("links");
            for (Map<String, Object> link : links) {
                if ("approve".equals(link.get("rel"))) {
                    return (String) link.get("href");
                }
            }
        }
        throw new PaymentException("Échec de la requête PayPal: " + response.getBody());
    }

    @Override
    @Transactional
    public void processWebhook(String provider, Map<String, Object> payload) {
        try {
            String reference = (String) payload.get("reference");
            if (reference == null) {
                reference = (String) ((Map<String, Object>) payload.get("resource")).get("custom_id");
            }

            Paiement paiement = paiementRepository.findByReferencePaiement(reference)
                    .orElseThrow(() -> new PaymentException("Paiement non trouvé"));

            switch (provider.toLowerCase()) {
                case "momo":
                    handleMomoWebhook(payload, paiement);
                    break;
                case "om":
                    handleOMWebhook(payload, paiement);
                    break;
                case "paypal":
                    handlePayPalWebhook(payload, paiement);
                    break;
                default:
                    log.warn("Fournisseur de webhook non supporté: {}", provider);
            }
            paiementRepository.save(paiement);
        } catch (Exception e) {
            log.error("Erreur de traitement du webhook", e);
            throw new PaymentException("Erreur de traitement webhook: " + e.getMessage());
        }
    }

    private void handleMomoWebhook(Map<String, Object> payload, Paiement paiement) {
        String status = (String) payload.get("status");
        if ("SUCCESSFUL".equals(status)) {
            paiement.setStatut(PaiementStatus.complete);
            paiement.setDatePaiement(LocalDateTime.now());
        } else {
            paiement.setStatut(PaiementStatus.echec);
        }
    }

    private void handleOMWebhook(Map<String, Object> payload, Paiement paiement) {
        String status = (String) payload.get("status");
        if ("SUCCESS".equals(status)) {
            paiement.setStatut(PaiementStatus.complete);
            paiement.setDatePaiement(LocalDateTime.now());
        } else {
            paiement.setStatut(PaiementStatus.echec);
        }
    }

    private void handlePayPalWebhook(Map<String, Object> payload, Paiement paiement) {
        String eventType = (String) payload.get("event_type");
        Map<String, Object> resource = (Map<String, Object>) payload.get("resource");
        
        if ("CHECKOUT.ORDER.APPROVED".equals(eventType)) {
            paiement.setStatut(PaiementStatus.complete);
            paiement.setDatePaiement(LocalDateTime.now());
        } else if ("PAYMENT.CAPTURE.DENIED".equals(eventType)) {
            paiement.setStatut(PaiementStatus.echec);
        }
    }

    private String generateTransactionId(PaiementMethode methode) {
        return methode.name() + "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    public String calculateHMAC(String data, String secret) {
    try {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        byte[] result = sha256_HMAC.doFinal(data.getBytes());
        return Hex.encodeHexString(result);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
        throw new PaymentException("Erreur de génération de signature HMAC");
    }
}

}