package ${package}.factory;

import ${package}.domain.model.Product;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductFactory {
    
    private static final Faker faker = new Faker();
    
    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .id(faker.number().randomNumber())
                .name(faker.commerce().productName())
                .price(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000)))
                .createdAt(LocalDateTime.now());
    }
    
    public static Product.ProductBuilder aValidProduct() {
        return aProduct()
                .name(faker.commerce().productName())
                .price(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)));
    }
    
    public static Product.ProductBuilder anInvalidProduct() {
        return aProduct()
                .name("")
                .price(BigDecimal.ZERO);
    }
    
    public static Product.ProductBuilder aProductWithName(String name) {
        return aProduct().name(name);
    }
    
    public static Product.ProductBuilder aProductWithPrice(BigDecimal price) {
        return aProduct().price(price);
    }
}
