package mx.com.parrot

import groovy.util.logging.Slf4j
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@Slf4j
class LocalAuthProvider implements AuthenticationProvider {
  @Inject
  IdentityStore keyStore

  @Override
  Publisher<AuthenticationResponse> authenticate(HttpRequest httpRequest, AuthenticationRequest authenticationRequest) {
    String username = authenticationRequest.getIdentity().toString()
    String password = authenticationRequest.getSecret().toString()
    Mono.<AuthenticationResponse>create(emitter -> {
      if (password == keyStore.getUserPassword(username)) {
        emitter.success(AuthenticationResponse.success(username,[keyStore.getUserRole(username)]))
      } else {
        emitter.error(AuthenticationResponse.exception())
      }
    })
  }
}
