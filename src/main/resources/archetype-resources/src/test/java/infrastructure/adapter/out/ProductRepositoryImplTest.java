package ${package}.infrastructure.adapter.out;

import ${package}.domain.model.Product;
import ${package}.factory.ProductEntityFactory;
import ${package}.factory.ProductFactory;
import ${package}.infrastructure.adapter.out.mapper.ProductPersistenceMapper;
import ${package}.infrastructure.persistence.ProductEntity;
import ${package}.infrastructure.persistence.ProductR2dbcRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    @Mock
    private ProductR2dbcRepository r2dbcRepository;

    @Mock
    private ProductPersistenceMapper mapper;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @Test
    void shouldSaveProduct() {
        // Given
        Product product = ProductFactory.aValidProduct().build();
        ProductEntity entity = ProductEntityFactory.aValidProductEntity();
        ProductEntity savedEntity = ProductEntityFactory.aValidProductEntity();
        Product savedProduct = ProductFactory.aValidProduct().build();

        when(mapper.toEntity(product)).thenReturn(entity);
        when(r2dbcRepository.save(entity)).thenReturn(Mono.just(savedEntity));
        when(mapper.toDomain(savedEntity)).thenReturn(savedProduct);

        // When & Then
        StepVerifier.create(productRepository.save(product))
                .expectNext(savedProduct)
                .verifyComplete();
    }

    @Test
    void shouldFindById() {
        // Given
        Long id = 1L;
        ProductEntity entity = ProductEntityFactory.aValidProductEntity();
        Product product = ProductFactory.aValidProduct().build();

        when(r2dbcRepository.findById(id)).thenReturn(Mono.just(entity));
        when(mapper.toDomain(entity)).thenReturn(product);

        // When & Then
        StepVerifier.create(productRepository.findById(id))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void shouldFindAll() {
        // Given
        ProductEntity entity1 = ProductEntityFactory.aValidProductEntity();
        ProductEntity entity2 = ProductEntityFactory.aValidProductEntity();
        Product product1 = ProductFactory.aValidProduct().build();
        Product product2 = ProductFactory.aValidProduct().build();

        when(r2dbcRepository.findAll()).thenReturn(Flux.just(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(product1);
        when(mapper.toDomain(entity2)).thenReturn(product2);

        // When & Then
        StepVerifier.create(productRepository.findAll())
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }
}
