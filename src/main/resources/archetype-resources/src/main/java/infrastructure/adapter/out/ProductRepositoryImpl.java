package ${package}.infrastructure.adapter.out;

import ${package}.domain.model.Product;
import ${package}.domain.port.out.ProductRepository;
import ${package}.infrastructure.adapter.out.mapper.ProductPersistenceMapper;
import ${package}.infrastructure.persistence.ProductR2dbcRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductR2dbcRepository r2dbcRepository;
    private final ProductPersistenceMapper mapper;
    private final ObservationRegistry observationRegistry;

    @Override
    public Mono<Product> save(Product product) {
        return Observation.createNotStarted("product.repository.save", observationRegistry)
                .observe(() -> {
                    log.debug("Saving product: {}", product.getName());
                    
                    return Mono.just(product)
                            .map(mapper::toEntity)
                            .flatMap(r2dbcRepository::save)
                            .map(mapper::toDomain)
                            .doOnSuccess(saved -> log.debug("Product saved with id: {}", saved.getId()));
                });
    }

    @Override
    public Mono<Product> findById(Long id) {
        return Observation.createNotStarted("product.repository.findById", observationRegistry)
                .observe(() -> {
                    log.debug("Finding product by id: {}", id);
                    
                    return r2dbcRepository.findById(id)
                            .map(mapper::toDomain)
                            .doOnSuccess(product -> log.debug("Product found: {}", product.getName()))
                            .switchIfEmpty(Mono.fromRunnable(() -> log.debug("Product not found with id: {}", id)));
                });
    }

    @Override
    public Flux<Product> findAll() {
        return Observation.createNotStarted("product.repository.findAll", observationRegistry)
                .observe(() -> {
                    log.debug("Finding all products");
                    
                    return r2dbcRepository.findAll()
                            .map(mapper::toDomain)
                            .doOnComplete(() -> log.debug("Finished finding all products"));
                });
    }
}
