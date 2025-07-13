package dev.javokhir.ecommerce.kafka;

import dev.javokhir.ecommerce.customer.CustomerResponse;
import dev.javokhir.ecommerce.order.Order.PaymentMethod;
import dev.javokhir.ecommerce.product.PurchaseRequest;
import dev.javokhir.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
