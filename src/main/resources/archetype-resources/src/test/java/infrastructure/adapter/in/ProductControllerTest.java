package ${package}.infrastructure.adapter.in;

import ${package}.domain.model.Product;
import ${package}.domain.port.in.CreateProductUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreateProductUseCase createProductUseCase;

    @Test
    void shouldCreateProduct() {
        // Given
        Product product = new Product("Test Product", new BigDecimal("29.99"));
        product.setId(1L);

        when(createProductUseCase.create(eq("Test Product"), eq(new BigDecimal("29.99"))))
                .thenReturn(Mono.just(product));

        // When & Then
        webTestClient.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":\"Test Product\",\"price\":29.99}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test Product")
                .jsonPath("$.price").isEqualTo(29.99);
    }
}
