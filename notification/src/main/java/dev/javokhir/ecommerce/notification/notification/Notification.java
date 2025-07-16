package dev.javokhir.ecommerce.notification.notification;

import dev.javokhir.ecommerce.notification.kafka.order.OrderConfirmation;
import dev.javokhir.ecommerce.notification.kafka.payment.PaymentConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document
public class Notification {

    public enum NotificationType {
        ORDER_CONFIRMATION,
        PAYMENT_CONFIRMATION
    }

    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationDate;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;

}
