package ${package}.infrastructure.adapter.in.mapper;

import ${package}.domain.model.Product;
import ${package}.factory.ProductFactory;
import ${package}.infrastructure.adapter.in.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductControllerMapperTest {

    @Autowired
    private ProductControllerMapper mapper;

    @Test
    void shouldMapProductToResponse() {
        // Given
        Product product = ProductFactory.aValidProduct().build();

        // When
        ProductResponse response = mapper.toResponse(product);

        // Then
        assertThat(response.id()).isEqualTo(product.getId());
        assertThat(response.name()).isEqualTo(product.getName());
        assertThat(response.price()).isEqualTo(product.getPrice());
        assertThat(response.createdAt()).isEqualTo(product.getCreatedAt());
    }
}
