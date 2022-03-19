package mx.com.parrot.service.dto

import io.micronaut.core.annotation.Introspected

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * A DTO for the {@link mx.com.parrot.domain.Order} entity.
 */
@Introspected
@EqualsAndHashCode
@ToString
class OrderDetailDTO implements Serializable{
  Long id
  OrderDTO orderDTO
  ProductDTO productDTO
}
