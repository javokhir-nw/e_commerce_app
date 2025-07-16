package dev.javokhir.ecommerce.payment;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-url}"
)
public class PaymentClient {

    @PostMapping
    public Integer requestOrderPayment(@RequestBody PaymentRequest request){

    }
}
