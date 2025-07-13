package dev.javokhir.ecommerce.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class CustomerNotFountException extends RuntimeException {
    private final String msg;
}
