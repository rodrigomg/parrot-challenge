package mx.com.parrot.web.rest

import mx.com.parrot.service.UserService
import mx.com.parrot.service.dto.UserDTO
import mx.com.parrot.utils.HeaderUtil
import mx.com.parrot.utils.PaginationUtil
import mx.com.parrot.web.errors.BadRequestAlertException
import io.micronaut.context.annotation.Value
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.uri.UriBuilder
import io.micronaut.http.annotation.*
import java.net.URI
import java.net.URISyntaxException
import java.util.List
import java.util.Optional
import jakarta.inject.Inject
import groovy.util.logging.Slf4j

/**
 * REST controller for managing {@link mx.com.parrot.domain.User}.
 */
@Slf4j
@Controller("/api")
class UserResource{

  private static final String ENTITY_NAME = "user"
  @Value('${micronaut.application.name}')
  private String applicationName


  @Inject
  UserService userService

  /**
   * {@code POST  /users} : Create a new user.
   *
   * @param userDTO the userDTO to create.
   * @return the {@link HttpResponse} with status {@code 201 (Created)} and with body the new userDTO, or with status {@code 400 (Bad Request)} if the user has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @Post("/users")
  HttpResponse<UserDTO> createUser(@Body UserDTO userDTO) throws URISyntaxException {
    log.debug("REST request to save User : ${userDTO}")
    if (userDTO.getId() != null) {
      throw new BadRequestAlertException("A new user cannot already have an ID", ENTITY_NAME, "idexists")
    }
    UserDTO result = userService.save(userDTO)
    URI location = new URI("/api/users/${result.id}")
    HttpResponse.created(result).headers(headers -> {
      headers.location(location)
      HeaderUtil.createEntityCreationAlert(headers, applicationName, true, ENTITY_NAME, result.id.toString())
    })
  }

}