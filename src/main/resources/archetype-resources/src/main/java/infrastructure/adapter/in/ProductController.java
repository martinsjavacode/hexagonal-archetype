package ${package}.infrastructure.adapter.in;

import ${package}.domain.port.in.CreateProductUseCase;
import ${package}.infrastructure.adapter.in.dto.CreateProductRequest;
import ${package}.infrastructure.adapter.in.dto.ProductResponse;
import ${package}.infrastructure.adapter.in.mapper.ProductControllerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final ProductControllerMapper mapper;

    @PostMapping
    public Mono<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return createProductUseCase.create(request.name(), request.price())
                .map(mapper::toResponse);
    }
}
