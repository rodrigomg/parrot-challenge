package mx.com.parrot.service.mapper

import mx.com.parrot.domain.User
import mx.com.parrot.service.dto.UserDTO
import groovy.util.logging.Slf4j
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.Optional

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}.
 */

@Slf4j
@Singleton
class UserMapper implements EntityMapper<UserDTO,User>{

	Optional<User> toEntity(UserDTO dto){
		User entity = new User()
		entity.id = dto.id
		entity.email = dto.email
		entity.firstName = dto.firstName
		entity.lastName = dto.lastName
		Optional.of(entity)
	}

	Optional<UserDTO> toDto(User entity){
		UserDTO dto = new UserDTO()
		dto.id = entity.id
		dto.email = entity.email
		dto.firstName = entity.firstName
		dto.lastName = entity.lastName
		Optional.of(dto)
	}

	Page<User> toEntityList(List<UserDTO> dtoList){
		List<User> entityList = new ArrayList<User>()
		dtoList.collect { dto ->
			User entity = new User()
			entity.id = dto.id
			entity.email = dto.email
			entity.firstName = dto.firstName
			entity.lastName = dto.lastName
			entityList.add(entity)
		}
		Page.of(entityList, Pageable.unpaged(),entityList.size())
	}

	Page<UserDTO> toDtoList(List<User> entityList){
		List<UserDTO> dtoList = new ArrayList<UserDTO>()
		entityList.collect{entity ->
			UserDTO dto = new UserDTO()
			dto.id = entity.id
			dto.email = entity.email
			dto.firstName = entity.firstName
			dto.lastName = entity.lastName
			dtoList.add(dto)
		}
		Page.of(dtoList, Pageable.unpaged(),dtoList.size())
	}

}
