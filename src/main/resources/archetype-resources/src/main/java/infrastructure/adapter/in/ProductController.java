package ${package}.infrastructure.adapter.in;

import ${package}.domain.port.in.CreateProductUseCase;
import ${package}.infrastructure.adapter.in.dto.CreateProductRequest;
import ${package}.infrastructure.adapter.in.dto.ProductResponse;
import ${package}.infrastructure.adapter.in.mapper.ProductControllerMapper;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final ProductControllerMapper mapper;
    private final ObservationRegistry observationRegistry;

    @PostMapping
    public Mono<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return Observation.createNotStarted("product.controller.create", observationRegistry)
                .observe(() -> {
                    log.info("Received request to create product: {}", request.name());
                    
                    return createProductUseCase.create(request.name(), request.price())
                            .map(mapper::toResponse)
                            .doOnSuccess(response -> log.info("Product created with id: {}", response.id()));
                });
    }
}
