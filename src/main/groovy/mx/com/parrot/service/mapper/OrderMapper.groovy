package mx.com.parrot.service.mapper

import mx.com.parrot.domain.Order
import mx.com.parrot.domain.User
import mx.com.parrot.service.dto.OrderDTO
import groovy.util.logging.Slf4j
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.Optional

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */

@Slf4j
@Singleton
class OrderMapper implements EntityMapper<OrderDTO,Order>{

  @Inject
  UserMapper userMapper

	Optional<Order> toEntity(OrderDTO dto){
		Order entity = new Order()
		entity.id = dto.id
		entity.user = userMapper.toEntity(dto.name).get
		Optional.of(entity)
	}

	Optional<OrderDTO> toDto(Order entity){
		OrderDTO dto = new OrderDTO()
		dto.id = entity.id
		dto.user = userMapper.toDto(entity.user).get()
		Optional.of(dto)
	}

	Page<Order> toEntityList(List<OrderDTO> dtoList){
		List<Order> entityList = new ArrayList<Order>()
		dtoList.collect { dto ->
			Order entity = new Order()
			entity.id = dto.id
		  entity.user = userMapper.toEntity(dto.name).get
			entityList.add(entity)
		}
		Page.of(entityList, Pageable.unpaged(),entityList.size())
	}

	Page<OrderDTO> toDtoList(List<Order> entityList){
		List<OrderDTO> dtoList = new ArrayList<OrderDTO>()
		entityList.collect{entity ->
			OrderDTO dto = new OrderDTO()
			dto.id = entity.id
		  dto.user = userMapper.toDto(entity.user).get()
			dtoList.add(dto)
		}
		Page.of(dtoList, Pageable.unpaged(),dtoList.size())
	}


}
