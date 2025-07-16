package dev.javokhir.ecommerce.order;

import dev.javokhir.ecommerce.customer.CustomerClient;
import dev.javokhir.ecommerce.exception.BusinessException;
import dev.javokhir.ecommerce.kafka.OrderConfirmation;
import dev.javokhir.ecommerce.kafka.OrderProducer;
import dev.javokhir.ecommerce.orderline.OrderLineRequest;
import dev.javokhir.ecommerce.orderline.OrderLineService;
import dev.javokhir.ecommerce.payment.PaymentClient;
import dev.javokhir.ecommerce.payment.PaymentRequest;
import dev.javokhir.ecommerce.product.ProductClient;
import dev.javokhir.ecommerce.product.PurchaseRequest;
import dev.javokhir.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;
    private final OrderMapper mapper;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can't find customer by id: " + request.customerId()));

        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(request.products());

        var order = orderRepository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);


        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(mapper::fromOrder).toList();
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository
                .findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID:: %s", orderId)));
    }
}
