package dev.javokhir.ecommerce.notification;

import dev.javokhir.ecommerce.payment.Payment.*;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}
