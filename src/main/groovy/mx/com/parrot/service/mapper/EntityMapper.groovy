package mx.com.parrot.service.mapper

import io.micronaut.data.model.Page

import java.util.List

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

trait EntityMapper <D, E> {

  abstract Optional<E> toEntity(D dto)
  abstract Optional<D> toDto(E entity)
  abstract Page<E> toEntityList(List<D> dtoList)
  abstract Page<D> toDtoList(List<E> entityList)
}
