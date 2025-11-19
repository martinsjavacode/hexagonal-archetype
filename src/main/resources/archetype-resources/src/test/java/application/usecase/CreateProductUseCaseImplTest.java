package ${package}.application.usecase;

import ${package}.domain.model.Product;
import ${package}.domain.port.out.ProductRepository;
import ${package}.domain.service.ProductDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDomainService productDomainService;

    private CreateProductUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateProductUseCaseImpl(productRepository, productDomainService);
    }

    @Test
    void shouldCreateProduct() {
        // Given
        String name = "Test Product";
        BigDecimal price = new BigDecimal("29.99");
        Product savedProduct = new Product(name, price);
        savedProduct.setId(1L);

        doNothing().when(productDomainService).validateProduct(any(Product.class));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(savedProduct));

        // When & Then
        StepVerifier.create(useCase.create(name, price))
                .expectNext(savedProduct)
                .verifyComplete();

        verify(productDomainService).validateProduct(any(Product.class));
        verify(productRepository).save(any(Product.class));
    }
}
