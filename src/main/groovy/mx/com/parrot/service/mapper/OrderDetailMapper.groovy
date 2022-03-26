package mx.com.parrot.service.mapper

import mx.com.parrot.domain.Order
import mx.com.parrot.domain.Product
import mx.com.parrot.service.dto.OrderDTO
import mx.com.parrot.service.dto.ProductDTO
import groovy.util.logging.Slf4j
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.Optional
import mx.com.parrot.service.dto.OrderDetailDTO
import mx.com.parrot.domain.OrderDetail

/**
 * Mapper for the entity {@link OrderDetail} and its DTO {@link OrderDetailDTO}.
 */

@Slf4j
@Singleton
class OrderDetailMapper implements EntityMapper<OrderDetailDTO,OrderDetail>{

  @Inject
  ProductMapper productMapper

	Optional<OrderDetail> toEntity(OrderDetailDTO dto){
		OrderDetail entity = new OrderDetail()
    Order order = new Order()
    entity.order = order
		entity.id = dto.id
    entity.order.id = dto.orderId
    entity.product = productMapper.toEntity(dto.productDTO).get()
		Optional.of(entity)
	}

	Optional<OrderDetailDTO> toDto(OrderDetail entity){
		OrderDetailDTO dto = new OrderDetailDTO()
		dto.id = entity.id
    dto.orderId = entity.order.id
    dto.productDTO = productMapper.toDto(entity.product).get()
		Optional.of(dto)
	}

	Page<OrderDetail> toEntityList(List<OrderDetailDTO> dtoList){
		List<OrderDetail> entityList = new ArrayList<OrderDetail>()
		dtoList.collect { dto ->
			OrderDetail entity = new OrderDetail()
      Order order = new Order()
      entity.order = order
		  entity.id = dto.id
      entity.order.id = dto.orderId
      entity.product = productMapper.toEntity(dto.productDTO).get()
			entityList.add(entity)
		}
		Page.of(entityList, Pageable.unpaged(),entityList.size())
	}

	Page<OrderDetailDTO> toDtoList(List<OrderDetail> entityList){
		List<OrderDetailDTO> dtoList = new ArrayList<OrderDetailDTO>()
		entityList.collect{entity ->
			OrderDetailDTO dto = new OrderDetailDTO()
		  dto.id = entity.id
      dto.orderId = entity.order.id
      dto.productDTO = productMapper.toDto(entity.product).get()
			dtoList.add(dto)
		}
		Page.of(dtoList, Pageable.unpaged(),dtoList.size())
	}

}
