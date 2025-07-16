package dev.javokhir.ecommerce.notification.kafka;

import dev.javokhir.ecommerce.notification.email.EmailService;
import dev.javokhir.ecommerce.notification.kafka.order.OrderConfirmation;
import dev.javokhir.ecommerce.notification.kafka.payment.PaymentConfirmation;
import dev.javokhir.ecommerce.notification.notification.Notification;
import dev.javokhir.ecommerce.notification.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation confirmation){
        log.info("Consuming the message from payment-topic Topic:: {}", confirmation);
        repository.save(
                Notification.builder()
                        .type(Notification.NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(confirmation)
                .build());

       var customerName = confirmation.customerFirstName() + " " + confirmation.customerLastName();

        emailService.sendPaymentSuccessEmail(
                confirmation.customerEmail(),
                customerName,
                confirmation.amount(),
                confirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation confirmation){
        log.info("Consuming the message from order-topic Topic:: {}", confirmation);
        repository.save(
                Notification.builder()
                        .type(Notification.NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(confirmation)
                        .build());

        var customerName = confirmation.customer().firstName() + " " + confirmation.customer().lastName();

        emailService.sendOrderConfirmationEmail(
                confirmation.customer().email(),
                customerName,
                confirmation.totalAmount(),
                confirmation.orderReference(),
                confirmation.products()
        );
    }

}
