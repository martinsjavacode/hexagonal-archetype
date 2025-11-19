package ${package}.factory;

import ${package}.infrastructure.persistence.ProductEntity;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductEntityFactory {
    
    private static final Faker faker = new Faker();
    
    public static ProductEntity aProductEntity() {
        ProductEntity entity = new ProductEntity(
                faker.commerce().productName(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000)),
                LocalDateTime.now()
        );
        entity.setId(faker.number().randomNumber());
        return entity;
    }
    
    public static ProductEntity aValidProductEntity() {
        ProductEntity entity = new ProductEntity(
                faker.commerce().productName(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)),
                LocalDateTime.now()
        );
        entity.setId(faker.number().randomNumber());
        return entity;
    }
    
    public static ProductEntity aProductEntityWithName(String name) {
        ProductEntity entity = new ProductEntity(
                name,
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)),
                LocalDateTime.now()
        );
        entity.setId(faker.number().randomNumber());
        return entity;
    }
}
