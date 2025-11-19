package ${package}.infrastructure.adapter.out.mapper;

import ${package}.domain.model.Product;
import ${package}.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductPersistenceMapperTest {

    @Autowired
    private ProductPersistenceMapper mapper;

    @Test
    void shouldMapProductToEntity() {
        Product product = new Product("Test Product", new BigDecimal("29.99"));
        product.setId(1L);

        ProductEntity entity = mapper.toEntity(product);

        assertThat(entity.getId()).isEqualTo(product.getId());
        assertThat(entity.getName()).isEqualTo(product.getName());
        assertThat(entity.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void shouldMapEntityToProduct() {
        ProductEntity entity = new ProductEntity("Test Product", new BigDecimal("29.99"), LocalDateTime.now());
        entity.setId(1L);

        Product product = mapper.toDomain(entity);

        assertThat(product.getId()).isEqualTo(entity.getId());
        assertThat(product.getName()).isEqualTo(entity.getName());
        assertThat(product.getPrice()).isEqualTo(entity.getPrice());
        assertThat(product.getCreatedAt()).isEqualTo(entity.getCreatedAt());
    }
}
