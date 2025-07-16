package dev.javokhir.ecommerce.payment;

import dev.javokhir.ecommerce.customer.CustomerResponse;
import dev.javokhir.ecommerce.order.Order;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        Order.PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
