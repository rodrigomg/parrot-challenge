package mx.com.parrot.service.mapper

import mx.com.parrot.domain.Product
import mx.com.parrot.service.dto.ProductDTO
import groovy.util.logging.Slf4j
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.Optional

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */

@Slf4j
@Singleton
class ProductMapper implements EntityMapper<ProductDTO,Product>{

	Optional<Product> toEntity(ProductDTO dto){
		Product entity = new Product()
		entity.id = dto.id
		entity.name = dto.name
		entity.price = dto.price
		entity.stock = dto.stock
		Optional.of(entity)
	}

	Optional<ProductDTO> toDto(Product entity){
		ProductDTO dto = new ProductDTO()
		dto.id = entity.id
		dto.name = entity.name
		dto.price = entity.price
		dto.stock = entity.stock
		Optional.of(dto)
	}

	Page<Product> toEntityList(List<ProductDTO> dtoList){
		List<Product> entityList = new ArrayList<Product>()
		dtoList.collect { dto ->
			Product entity = new Product()
			entity.id = dto.id
			entity.name = dto.name
			entity.price = dto.price
			entity.stock = dto.stock
			entityList.add(entity)
		}
		Page.of(entityList, Pageable.unpaged(),entityList.size())
	}

	Page<ProductDTO> toDtoList(List<Product> entityList){
		List<ProductDTO> dtoList = new ArrayList<ProductDTO>()
		entityList.collect{entity ->
			ProductDTO dto = new ProductDTO()
			dto.id = entity.id
			dto.name = entity.name
			dto.price = entity.price
			dto.stock = entity.stock
			dtoList.add(dto)
		}
		Page.of(dtoList, Pageable.unpaged(),dtoList.size())
	}

}
