package com.lendingApp.main.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lendingApp.main.entity.PaymentLog;
import com.lendingApp.main.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-checkout-session/{installmentId}")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@PathVariable UUID installmentId) {
        try {
            Session session = paymentService.createPaymentSession(installmentId);

            // Return URL instead of session object
            Map<String, String> response = new HashMap<>();
            response.put("url", session.getUrl());  // <-- session.getUrl() gives the Stripe-hosted checkout page
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }



    @PostMapping("/success/{installmentId}")
    public ResponseEntity< PaymentLog> handlePaymentSuccess(@RequestParam("session_id") String sessionId,@PathVariable UUID installmentId) throws StripeException {
        return ResponseEntity.ok(paymentService.handlePaymentSuccess(sessionId,installmentId));
    }
}