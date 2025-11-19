package ${package}.infrastructure.adapter.out.mapper;

import ${package}.domain.model.Product;
import ${package}.infrastructure.persistence.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductPersistenceMapper {
    
    ProductEntity toEntity(Product product);
    
    Product toDomain(ProductEntity entity);
}
