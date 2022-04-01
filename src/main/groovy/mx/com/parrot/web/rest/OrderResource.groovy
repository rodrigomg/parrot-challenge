package mx.com.parrot.web.rest

import mx.com.parrot.service.OrderService
import mx.com.parrot.service.dto.OrderDTO
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
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.net.URI
import java.net.URISyntaxException
import java.util.List
import java.util.Optional
import jakarta.inject.Inject
import groovy.util.logging.Slf4j
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

/**
 * REST controller for managing {@link mx.com.parrot.domain.User}.
 */

@Tag(name = "Orders")
@Slf4j
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/api")
class OrderResource{

  private static final String ENTITY_NAME = "order"
  @Value('${micronaut.application.name}')
  private String applicationName


  @Inject
  OrderService orderService
  /**
   * {@code POST  /orders} : Create a new order.
   *
   * @param orderDTO the orderDTO to create.
   * @return the {@link HttpResponse} with status {@code 201 (Created)} and with body the new orderDTO, or with status {@code 400 (Bad Request)} if the order has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @Post("/orders")
	@Operation(summary = "Create an order")
  @ApiResponse(
          responseCode = "200", description = "Order created",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class))))
  HttpResponse<OrderDTO> createUser(@Body OrderDTO orderDTO) throws URISyntaxException {
    log.debug("REST request to save User : ${orderDTO}")
    if (orderDTO.getId() != null) {
      throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists")
    }
    OrderDTO result = orderService.save(orderDTO)
    if(!result.userDTO){
      throw new BadRequestAlertException("Validate data", ENTITY_NAME, "usernotexist")
    }
    URI location = new URI("/api/orders/${result.id}")
    HttpResponse.created(result).headers(headers -> {
      headers.location(location)
      HeaderUtil.createEntityCreationAlert(headers, applicationName, true, ENTITY_NAME, result.id.toString())
    })
  }

  /**
   * {@code PUT  /orders} : Updates an existing order.
   *
   * @param orderDTO the orderDTO to update.
   * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated orderDTO,
   * or with status {@code 400 (Bad Request)} if the orderDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the orderDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @Put("/orders")
	@Operation(summary = "Update an order")
  @ApiResponse(
          responseCode = "200", description = "Order updated",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class))))
  HttpResponse<OrderDTO> updateUser(@Body OrderDTO orderDTO) throws URISyntaxException {
    log.debug("REST request to update User : ${orderDTO}")
    if (orderDTO.id == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
    }
    OrderDTO result = orderService.save(orderDTO)
    HttpResponse.ok(result).headers(headers ->
        HeaderUtil.createEntityUpdateAlert(headers, applicationName, true, ENTITY_NAME, orderDTO.id.toString()))
  }

  /**
   * {@code GET  /orders} : get all the orders.
   *
   * @param pageable the pagination information.
   * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of orders in body.
   */
  @Get("/orders")
	@Operation(summary = "Get all orders")
  @ApiResponse(
          responseCode = "200", description = "Get sucess",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class))))
  HttpResponse<List<OrderDTO>> getAllUsers(HttpRequest request, Pageable pageable) {
    log.debug("REST request to get a page of Users")
    Page<OrderDTO> page = orderService.findAll(pageable)
    HttpResponse.ok(page.getContent()).headers(headers ->
        PaginationUtil.generatePaginationHttpHeaders(headers, UriBuilder.of(request.getPath()), page))
  }

  /**
   * {@code GET  /orders/:id} : get the "id" order.
   *
   * @param id the id of the orderDTO to retrieve.
   * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the orderDTO, or with status {@code 404 (Not Found)}.
   */
  @Get("/orders/{id}")
	@Operation(summary = "Get an order by id")
  @ApiResponse(
          responseCode = "200", description = "Get sucess",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class))))
  Optional<OrderDTO> getUser(@PathVariable Long id) {
    log.debug("REST request to get User : ${id}")
    orderService.findOne(id)
  }

  /**
   * {@code DELETE  /orders/:id} : delete the "id" order.
   *
   * @param id the id of the orderDTO to delete.
   * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
   */
  @Delete("/orders/{id}")
	@Operation(summary = "Delete an order by id")
  @ApiResponse(
          responseCode = "200", description = "Order deleted",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class))))
  HttpResponse deleteUser(@PathVariable Long id) {
    log.debug("REST request to delete User : ${id}")
    orderService.delete(id)
    HttpResponse.noContent().headers(headers -> HeaderUtil.createEntityDeletionAlert(headers, applicationName, true, ENTITY_NAME, id.toString()))
  }

}
