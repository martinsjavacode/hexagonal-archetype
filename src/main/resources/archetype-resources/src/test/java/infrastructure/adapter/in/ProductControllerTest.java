package ${package}.infrastructure.adapter.in;

import ${package}.domain.model.Product;
import ${package}.domain.port.in.CreateProductUseCase;
import ${package}.factory.ProductFactory;
import ${package}.factory.ProductRequestFactory;
import ${package}.infrastructure.adapter.in.dto.CreateProductRequest;
import ${package}.infrastructure.adapter.in.dto.ProductResponse;
import ${package}.infrastructure.adapter.in.mapper.ProductControllerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private ProductControllerMapper mapper;

    @InjectMocks
    private ProductController productController;

    @Test
    void shouldCreateProduct() {
        // Given
        CreateProductRequest request = ProductRequestFactory.aValidRequest();
        Product product = ProductFactory.aValidProduct()
                .name(request.name())
                .price(request.price())
                .build();
        
        ProductResponse response = new ProductResponse(
                product.getId(), 
                product.getName(), 
                product.getPrice(), 
                LocalDateTime.now()
        );

        when(createProductUseCase.create(eq(request.name()), eq(request.price())))
                .thenReturn(Mono.just(product));
        when(mapper.toResponse(any(Product.class))).thenReturn(response);

        // When
        Mono<ProductResponse> result = productController.createProduct(request);

        // Then
        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
    }
}
