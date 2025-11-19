package ${package}.application.usecase;

import ${package}.domain.model.Product;
import ${package}.domain.port.in.CreateProductUseCase;
import ${package}.domain.port.out.ProductRepository;
import ${package}.domain.service.ProductDomainService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;
    private final ObservationRegistry observationRegistry;

    @Override
    public Mono<Product> create(String name, BigDecimal price) {
        return Observation.createNotStarted("product.create", observationRegistry)
                .observe(() -> {
                    log.info("Creating product with name: {} and price: {}", name, price);
                    
                    Product product = new Product(name, price);
                    productDomainService.validateProduct(product);
                    
                    return productRepository.save(product)
                            .doOnSuccess(savedProduct -> log.info("Product created successfully with id: {}", savedProduct.getId()))
                            .doOnError(error -> log.error("Error creating product: {}", error.getMessage()));
                });
    }
}
