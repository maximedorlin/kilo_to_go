package com.soutenence.kilotogo.service;

import com.soutenence.kilotogo.dto.PaymentRequestDTO;
import com.soutenence.kilotogo.entity.Paiement;
import java.util.Map;

public interface PaymentService {
    Paiement initierPaiement(PaymentRequestDTO request);
    void processWebhook(String provider, Map<String, Object> payload);
    String calculateHMAC(String data, String secret);
}