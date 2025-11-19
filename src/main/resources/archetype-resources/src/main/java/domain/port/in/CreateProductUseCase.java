package ${package}.domain.port.in;

import ${package}.domain.model.Product;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface CreateProductUseCase {
    Mono<Product> create(String name, BigDecimal price);
}
