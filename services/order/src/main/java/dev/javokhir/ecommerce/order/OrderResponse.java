package dev.javokhir.ecommerce.order;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        Order.PaymentMethod paymentMethod,
        String customerId
) {
}
