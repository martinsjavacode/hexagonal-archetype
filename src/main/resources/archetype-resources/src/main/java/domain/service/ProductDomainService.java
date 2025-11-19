package ${package}.domain.service;

import ${package}.domain.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductDomainService {
    
    public boolean isValidPrice(BigDecimal price) {
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    
    public void validateProduct(Product product) {
        if (!isValidName(product.getName())) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (!isValidPrice(product.getPrice())) {
            throw new IllegalArgumentException("Product price must be positive");
        }
    }
}
