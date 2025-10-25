package com.lendingApp.main.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.InstallmentDto;
import com.lendingApp.main.entity.PaymentLog;
import com.lendingApp.main.enums.InstallmentStatus;
import com.lendingApp.main.repository.PaymentLogRepository;
import com.lendingApp.main.service.InstallmentsSerivce;
import com.lendingApp.main.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentLogRepository paymentLogRepository;
    @Autowired
    private InstallmentsSerivce installmentsSerivce;

    // 🔹 1. Create Stripe Checkout Session
    @Override
    public Session createPaymentSession(UUID installmentId) throws StripeException {

        // Example: Fetch installment details (you can fetch from your InstallmentService or repo)
    	InstallmentDto installmentdto = this.installmentsSerivce.getInstallmentById(installmentId);
        double amount = installmentdto
        		.getInstallmentStatus()==InstallmentStatus.OVERDUE?
        		 installmentdto.getFineAmt()+installmentdto.getInstAmt()
        		:installmentdto.getInstAmt(); // Replace with actual installmentDto.getInstAmt() logic
        
        String currency = "inr";

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/customer/payment-success/" + installmentId + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:4200/customer/payment-failed")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(currency)
                                                .setUnitAmount((long) (amount * 100)) // Stripe uses paise
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Loan Installment Payment")
                                                                .build())
                                                .build())
                                .build())
                .build();

        return Session.create(params);
    }

    // 🔹 2. Handle Payment Success Callback
    @Override
    public PaymentLog handlePaymentSuccess(String sessionId,UUID installmentId) throws StripeException {
        Session session = Session.retrieve(sessionId);

        PaymentLog log = PaymentLog.builder()
                .installmentId(installmentId)// Pass in controller
                .amountPaid(session.getAmountTotal() / 100.0)
                .paymentStatus(session.getPaymentStatus())
                .paymentMethod("Stripe")
                .stripePaymentId(session.getId())
                .paymentDate(LocalDateTime.now())
                .remarks("Payment completed successfully")
                .build();

        updateInstallmentStatus(installmentId);
        return paymentLogRepository.save(log);
    }

    // 🔹 3. Update Installment Status Logic
    @Override
    public void updateInstallmentStatus(UUID installmentId) {
    	this.installmentsSerivce.updateInstallment(installmentId);
    }
}
