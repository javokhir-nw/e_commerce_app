package dev.javokhir.ecommerce.notification.email;

import lombok.Getter;

@Getter
public enum EmailTemplate {
    PAYMENT_CONFIRMATION("payment-confirmation.html","Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html","Order successfully processed");

    private final String template;
    private final String subject;

    EmailTemplate(String template,String subject){
        this.template = template;
        this.subject = subject;
    }
}
