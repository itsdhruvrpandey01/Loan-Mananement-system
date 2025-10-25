package com.lendingApp.main.service;

import java.util.UUID;

import com.lendingApp.main.entity.PaymentLog;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface PaymentService {

    Session createPaymentSession(UUID installmentId) throws StripeException;

    PaymentLog handlePaymentSuccess(String sessionId,UUID installmentId) throws StripeException;

    void updateInstallmentStatus(UUID installmentId);
}
