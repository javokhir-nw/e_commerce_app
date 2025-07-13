package dev.javokhir.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product is mandatory!")
        Integer productId,
        @Positive(message = "Product quantity should be positive!")
        double quantity
) {
}
