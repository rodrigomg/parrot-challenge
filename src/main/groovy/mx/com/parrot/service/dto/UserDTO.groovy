package mx.com.parrot.service.dto

import io.micronaut.core.annotation.Introspected

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * A DTO for the {@link mx.com.parrot.domain.User} entity.
 */
@Introspected
@EqualsAndHashCode
@ToString
class UserDTO implements Serializable{
  Long id
  String email
  String firstName
  String lastName
}
