package mx.com.parrot.service.dto

import io.micronaut.core.annotation.Introspected

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * A DTO for the {@link mx.com.parrot.domain.Product} entity.
 */
@Introspected
@EqualsAndHashCode
@ToString
class ProductDTO implements Serializable{
  Long id
  String name
  Float price
  Integer stock
}
