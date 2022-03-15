package mx.com.parrot.service.dto

import mx.com.parrot.domain.User
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
  User user
}
