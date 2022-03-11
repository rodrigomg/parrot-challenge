package mx.com.parrot.repository

import mx.com.parrot.domain.User
import groovy.util.logging.Slf4j
import io.micronaut.data.model.Pageable
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise


@Slf4j
@MicronautTest()
@Stepwise
class UserRepositorySpec extends Specification{

  @Inject
  UserRepository userRepository

  @Shared
  User user

  void setupSpec(){
    log.info("Setup user")
    user = new User()
  }

  void "Should create a new User"() {
    when:
    user.email = "rockdrigo.mtz@gmail.com"
    user.firstName = "Rodrigo"
    user.lastName = "Martinez Garcia"
    user = userRepository.mergeAndSave(user)

    then:
    log.info("User: ${user.toString()}")
    user.id
    user.email == "rockdrigo.mtz@gmail.com"

  }


}
