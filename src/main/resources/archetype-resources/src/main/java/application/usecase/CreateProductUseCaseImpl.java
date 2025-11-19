package ${package}.application.usecase;

import ${package}.domain.model.Product;
import ${package}.domain.port.in.CreateProductUseCase;
import ${package}.domain.port.out.ProductRepository;
import ${package}.domain.service.ProductDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;

    @Override
    public Mono<Product> create(String name, BigDecimal price) {
        Product product = new Product(name, price);
        productDomainService.validateProduct(product);
        return productRepository.save(product);
    }
}
