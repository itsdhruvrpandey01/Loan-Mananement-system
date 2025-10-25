package com.lendingApp.main.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lendingApp.main.entity.PaymentLog;

public interface PaymentLogRepository extends JpaRepository<PaymentLog, UUID> {
}
