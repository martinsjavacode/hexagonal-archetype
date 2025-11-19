package ${package}.infrastructure.adapter.out;

import ${package}.domain.model.Product;
import ${package}.domain.port.out.ProductRepository;
import ${package}.infrastructure.adapter.out.mapper.ProductPersistenceMapper;
import ${package}.infrastructure.persistence.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductR2dbcRepository r2dbcRepository;
    private final ProductPersistenceMapper mapper;

    @Override
    public Mono<Product> save(Product product) {
        return Mono.just(product)
                .map(mapper::toEntity)
                .flatMap(r2dbcRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return r2dbcRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Product> findAll() {
        return r2dbcRepository.findAll()
                .map(mapper::toDomain);
    }
}
