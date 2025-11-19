package ${package}.infrastructure.adapter.in.mapper;

import ${package}.domain.model.Product;
import ${package}.infrastructure.adapter.in.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductControllerMapper {
    
    ProductResponse toResponse(Product product);
}
