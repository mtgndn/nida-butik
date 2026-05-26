package org.example.taskmanager.service;

import org.example.taskmanager.dto.PaymentRequest;
import org.example.taskmanager.dto.PaymentResponse;
import org.example.taskmanager.entity.CustomerOrder;
import org.example.taskmanager.entity.OrderStatus;
import org.example.taskmanager.entity.Payment;
import org.example.taskmanager.entity.PaymentStatus;
import org.example.taskmanager.exception.BusinessRuleException;
import org.example.taskmanager.repository.OrderRepository;
import org.example.taskmanager.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderService orderService, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public PaymentResponse pay(PaymentRequest request) {
        CustomerOrder order = orderService.findOrder(request.orderId());
        if (order.getStatus() == OrderStatus.PAID) {
            throw new BusinessRuleException("Bu siparis zaten odendi.");
        }
        if (paymentRepository.existsByTransactionCodeIgnoreCase(request.transactionCode())) {
            throw new BusinessRuleException("Transaction kodu daha once kullanilmis.");
        }
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMethod(request.method());
        payment.setStatus(PaymentStatus.PAID);
        payment.setAmount(order.getTotalAmount());
        payment.setTransactionCode(request.transactionCode());
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
        return toResponse(paymentRepository.save(payment));
    }

    @Transactional(rollbackFor = Exception.class)
    public PaymentResponse rollbackDemo(PaymentRequest request) {
        PaymentResponse response = pay(request);
        throw new BusinessRuleException("Rollback testi: odeme bilerek iptal edildi.");
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getOrder().getId(), payment.getStatus(), payment.getAmount(), payment.getTransactionCode());
    }
}
