package ${package}.infrastructure.adapter.in.mapper;

import ${package}.domain.model.Product;
import ${package}.infrastructure.adapter.in.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductControllerMapperTest {

    @Autowired
    private ProductControllerMapper mapper;

    @Test
    void shouldMapProductToResponse() {
        Product product = new Product("Test Product", new BigDecimal("29.99"));
        product.setId(1L);
        product.setCreatedAt(LocalDateTime.now());

        ProductResponse response = mapper.toResponse(product);

        assertThat(response.id()).isEqualTo(product.getId());
        assertThat(response.name()).isEqualTo(product.getName());
        assertThat(response.price()).isEqualTo(product.getPrice());
        assertThat(response.createdAt()).isEqualTo(product.getCreatedAt());
    }
}
