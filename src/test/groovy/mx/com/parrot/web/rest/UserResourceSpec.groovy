package mx.com.parrot.web.rest

import mx.com.parrot.service.dto.UserDTO
import groovy.util.logging.Slf4j
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Shared
import spock.lang.Specification

@Slf4j
@MicronautTest
class UserResourceSpec extends Specification{

  @Inject
  @Client("/")
  HttpClient client

  @Shared
  UserDTO userDTO

  void setupSpec(){
    userDTO = new UserDTO(email: "qwerty@gmail.com", firstName: "qwerty", lastName: "qwertyuiop")
  }

  void "Should create a new user"() {
    when: "Create a new user"
    HttpResponse<UserDTO> response = client.toBlocking().exchange(HttpRequest.POST("/api/users",
    userDTO),UserDTO.class)
    userDTO = response.body()

    then: "Validate http status code CREATED"
    log.info("-----------------------------")
    log.info("---------------- RESPONSE: ${response.dump()} ------------------")
    log.info("----------------<User DTO: ${userDTO.dump()} ------------------")
    log.info("-----------------------------")
    response.status().code == HttpStatus.CREATED.code
    userDTO.id
  }
}
