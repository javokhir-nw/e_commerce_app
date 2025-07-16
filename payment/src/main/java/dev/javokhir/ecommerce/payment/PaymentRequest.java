package dev.javokhir.ecommerce.payment;

import java.math.BigDecimal;
import  dev.javokhir.ecommerce.payment.Payment.PaymentMethod;

public record PaymentRequest(
        Integer id,
        BigDecimal amount, //amount of the payment $
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
