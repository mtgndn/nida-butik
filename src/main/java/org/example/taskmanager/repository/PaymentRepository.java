package org.example.taskmanager.repository;

import org.example.taskmanager.entity.Payment;
import org.example.taskmanager.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder_Id(Long orderId);
    Optional<Payment> findByTransactionCodeIgnoreCase(String transactionCode);
    List<Payment> findByStatus(PaymentStatus status);
    boolean existsByTransactionCodeIgnoreCase(String transactionCode);
}
