package mx.com.parrot

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.core.convert.format.MapFormat


@ConfigurationProperties('identity-store')
class IdentityStore {

  @MapFormat
  Map<String, String> users
  @MapFormat
  Map<String, String> roles

  String getUserPassword(String username) {
    users.get(username)
  }

  String getUserRole(String username) {
    roles.get(username)
  }
}
