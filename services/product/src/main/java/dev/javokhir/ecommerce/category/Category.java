package dev.javokhir.ecommerce.category;

import dev.javokhir.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Product> products;
}
