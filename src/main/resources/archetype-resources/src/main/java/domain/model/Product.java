package ${package}.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }
}
