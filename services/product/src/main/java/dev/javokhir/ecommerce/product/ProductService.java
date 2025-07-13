package dev.javokhir.ecommerce.product;

import dev.javokhir.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest productRequest) {
        var product = mapper.toProduct(productRequest);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more product is not exists!");
        }

        var storedRequests = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequests.get(i);
            if (product.getQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient quantity for product  with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getQuantity() - productRequest.quantity();
            product.setQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }
        
        
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId).map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not fount by ID:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream().map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
