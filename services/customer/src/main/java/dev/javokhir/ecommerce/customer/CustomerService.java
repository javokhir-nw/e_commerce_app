package dev.javokhir.ecommerce.customer;

import dev.javokhir.ecommerce.exception.CustomerNotFountException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String create(@Valid CustomerRequest request) {
        if (request == null) {
            return null;
        }
        Customer customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void update(@Valid CustomerRequest request) {
        Customer customer = repository.findById(request.id()).orElseThrow(() -> new CustomerNotFountException(String.format("Cannot update customer:: No customer found with the provided ID:: %s ",request.id())));
        mergeCustomer(customer,request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())){
            customer.setFirstName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())){
            customer.setFirstName(request.email());
        }
        if (request.address() != null){
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAll() {
        return repository.findAll().stream().map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String id) {
        return repository.existsById(id);
    }

    public CustomerResponse findById(String id) {
        return mapper.fromCustomer(repository.findById(id).orElseThrow(() -> new CustomerNotFountException(String.format("Cannot find customer by ID:: %s ", id))));
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
