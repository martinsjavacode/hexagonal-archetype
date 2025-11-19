package ${package}.factory;

import ${package}.infrastructure.adapter.in.dto.CreateProductRequest;
import net.datafaker.Faker;

import java.math.BigDecimal;

public class ProductRequestFactory {
    
    private static final Faker faker = new Faker();
    
    public static CreateProductRequest aValidRequest() {
        return new CreateProductRequest(
                faker.commerce().productName(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500))
        );
    }
    
    public static CreateProductRequest anInvalidRequest() {
        return new CreateProductRequest("", BigDecimal.ZERO);
    }
    
    public static CreateProductRequest aRequestWithName(String name) {
        return new CreateProductRequest(
                name,
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500))
        );
    }
    
    public static CreateProductRequest aRequestWithPrice(BigDecimal price) {
        return new CreateProductRequest(
                faker.commerce().productName(),
                price
        );
    }
}
