package mx.com.parrot.service

import mx.com.parrot.service.dto.UserDTO
import mx.com.parrot.service.mapper.UserMapper
import groovy.util.logging.Slf4j
import mx.com.parrot.service.UserService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import spock.lang.Shared
import jakarta.inject.Inject

@Slf4j
@MicronautTest
class UserServiceSpec extends Specification{

  @Inject
  UserService userService
  @Inject
  UserMapper userMapper

  @Shared
  UserDTO userDTO

  void setupSpec(){
    userDTO = new UserDTO()
  }

  void "should create a new user"() {
    when:
    userDTO.email = "rockdrigo.mtz@gmail.com"
    userDTO.firstName = "rodrigo"
    userDTO.lastName = "martinez garcia"
    userDTO = userService.save(userDTO)

    then:
    log.info("userDTO: ${userDTO.toString()}")
    userDTO.id
    userDTO.email == "rockdrigo.mtz@gmail.com"

  }



}
