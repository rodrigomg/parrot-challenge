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

  void "Should get one user"() {
    when: 'Get a user by id'
    log.info("User to share ${userDTO.toString()}")
    UserDTO userDTOFromEndPoint = client.toBlocking().retrieve(HttpRequest.GET("/api/users/${userDTO.id}"), UserDTO.class)

    then:"return user by id"
    log.info("-----------------------------")
    log.info("User from endpont: ${userDTOFromEndPoint.toString()}")
    log.info("-----------------------------")
    userDTOFromEndPoint.id == userDTO.id
    userDTOFromEndPoint.firstName == userDTO.firstName
  }

  void "Should update a user"() {
    when:
    userDTO.lastName = "Bodoque"
    HttpResponse<UserDTO> response = client.toBlocking().exchange(HttpRequest.PUT("/api/users/", userDTO),UserDTO.class)
    UserDTO userUpdatedDTO = response.body()

    then:
    log.info("-----------------------------")
    log.info("${response.dump()}")
    log.info("User updated: ${userUpdatedDTO.dump()}")
    log.info("-----------------------------")
    response.status().code == HttpStatus.OK.code
    userUpdatedDTO.lastName == "Bodoque"
  }

  void "Should delete an user"() {
    when:
    HttpResponse<UserDTO> response = client.toBlocking().exchange(HttpRequest.DELETE("/api/users/${userDTO.id}"),UserDTO.class)

    then:
    log.info("-----------------------------")
    log.info("${response.dump()}")
    log.info("-----------------------------")
    response.status().code == HttpStatus.NO_CONTENT.code
  }

}
