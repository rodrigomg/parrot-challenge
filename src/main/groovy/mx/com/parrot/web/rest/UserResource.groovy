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

  /**
   * {@code PUT  /users} : Updates an existing user.
   *
   * @param userDTO the userDTO to update.
   * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated userDTO,
   * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @Put("/users")
  HttpResponse<UserDTO> updateUser(@Body UserDTO userDTO) throws URISyntaxException {
    log.debug("REST request to update User : ${userDTO}")
    if (userDTO.id == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
    }
    UserDTO result = userService.save(userDTO)
    HttpResponse.ok(result).headers(headers ->
        HeaderUtil.createEntityUpdateAlert(headers, applicationName, true, ENTITY_NAME, userDTO.id.toString()))
  }

  /**
   * {@code GET  /users} : get all the users.
   *
   * @param pageable the pagination information.
   * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of users in body.
   */
  @Get("/users")
  HttpResponse<List<UserDTO>> getAllUsers(HttpRequest request, Pageable pageable) {
    log.debug("REST request to get a page of Users")
    Page<UserDTO> page = userService.findAll(pageable)
    HttpResponse.ok(page.getContent()).headers(headers ->
        PaginationUtil.generatePaginationHttpHeaders(headers, UriBuilder.of(request.getPath()), page))
  }

  /**
   * {@code GET  /users/:id} : get the "id" user.
   *
   * @param id the id of the userDTO to retrieve.
   * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the userDTO, or with status {@code 404 (Not Found)}.
   */
  @Get("/users/{id}")
  Optional<UserDTO> getUser(@PathVariable Long id) {
    log.debug("REST request to get User : ${id}")
    userService.findOne(id)
  }

  /**
   * {@code DELETE  /users/:id} : delete the "id" user.
   *
   * @param id the id of the userDTO to delete.
   * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
   */
  @Delete("/users/{id}")
  HttpResponse deleteUser(@PathVariable Long id) {
    log.debug("REST request to delete User : ${id}")
    userService.delete(id)
    HttpResponse.noContent().headers(headers -> HeaderUtil.createEntityDeletionAlert(headers, applicationName, true, ENTITY_NAME, id.toString()))
  }

}
