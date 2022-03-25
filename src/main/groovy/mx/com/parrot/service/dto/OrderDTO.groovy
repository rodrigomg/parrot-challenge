package mx.com.parrot.service.dto

import mx.com.parrot.service.dto.UserDTO
import io.micronaut.core.annotation.Introspected

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * A DTO for the {@link mx.com.parrot.domain.Order} entity.
 */
@Introspected
@EqualsAndHashCode
@ToString
class OrderDTO implements Serializable{
  Long id
  UserDTO userDTO
  OrderDetailDTO orderDetailDTO
}
