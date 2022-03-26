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
@MicronautTest( rollback = false)
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

  void "Should create a new user"() {
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

  void "Should get one user"() {
    when:
    User userFromRepo = userRepository.findById(user.id).get()

    then:
    log.info("User: ${userFromRepo.toString()}")
    user.id
    user.email == "rockdrigo.mtz@gmail.com"
  }

  void "Should get all users"() {
    when:
    List<User> users = userRepository.findAll(Pageable.from(0,10)).getContent()

    then:
    log.info("Users: ${users.dump()}")
    user.id
    users.size() == 2
  }

  void "Should update an user"() {
    when:
    user.firstName = "Roger"
    user = userRepository.update(user)

    then:
    log.info("User: ${user.toString()}")
    user.id
    user.firstName == "Roger"
  }

  void "Should delete an user"() {
    when:
    userRepository.deleteById(user.id)

    then:
    !userRepository.findById(user.id).isPresent()
  }

  void "Should get one user by email"() {
    when:
    User userFromRepo = userRepository.findByEmail("roger.mtz@gmail.com")

    then:
    log.info("User: ${userFromRepo.toString()}")
    userFromRepo.id
    userFromRepo.email == "roger.mtz@gmail.com"
  }
}
