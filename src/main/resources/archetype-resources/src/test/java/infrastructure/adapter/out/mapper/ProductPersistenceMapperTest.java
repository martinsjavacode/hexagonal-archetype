package ${package}.infrastructure.adapter.out.mapper;

import ${package}.domain.model.Product;
import ${package}.factory.ProductEntityFactory;
import ${package}.factory.ProductFactory;
import ${package}.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductPersistenceMapperTest {

    @Autowired
    private ProductPersistenceMapper mapper;

    @Test
    void shouldMapProductToEntity() {
        // Given
        Product product = ProductFactory.aValidProduct().build();

        // When
        ProductEntity entity = mapper.toEntity(product);

        // Then
        assertThat(entity.getId()).isEqualTo(product.getId());
        assertThat(entity.getName()).isEqualTo(product.getName());
        assertThat(entity.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void shouldMapEntityToProduct() {
        // Given
        ProductEntity entity = ProductEntityFactory.aValidProductEntity();

        // When
        Product product = mapper.toDomain(entity);

        // Then
        assertThat(product.getId()).isEqualTo(entity.getId());
        assertThat(product.getName()).isEqualTo(entity.getName());
        assertThat(product.getPrice()).isEqualTo(entity.getPrice());
        assertThat(product.getCreatedAt()).isEqualTo(entity.getCreatedAt());
    }
}
