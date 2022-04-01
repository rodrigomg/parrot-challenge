package mx.com.parrot.web.rest

import mx.com.parrot.service.dto.OrderDTO
import mx.com.parrot.service.dto.OrderDetailDTO
import mx.com.parrot.service.dto.UserDTO
import mx.com.parrot.service.dto.ProductDTO
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
import spock.lang.Ignore

@Slf4j
@MicronautTest
class OrderResourceSpec extends Specification{

  @Inject
  @Client("/")
  HttpClient client

  @Shared
  OrderDTO orderDTO

  void setupSpec(){
    UserDTO userDTO = new UserDTO(email: "roger.mtz@gmail.com")
    Set<OrderDetailDTO> orderDetailsDTO = new HashSet<>()
    OrderDetailDTO orderDetailDTO = new OrderDetailDTO()
    ProductDTO productDTO = new ProductDTO()
    productDTO.name = "zanahoria"
    productDTO.price = 6.60
    productDTO.stock = 10

    orderDetailDTO.productDTO = productDTO
    orderDetailsDTO.add(orderDetailDTO)
    orderDTO = new OrderDTO(userDTO: userDTO, orderDetailsDTO: orderDetailsDTO)
  }

  void "Should create a new order"() {
    when: "Create a new order"
    HttpResponse<OrderDTO> response = client.toBlocking().exchange(HttpRequest.POST("/api/orders",
    orderDTO).basicAuth("rock", "rock@1"),OrderDTO.class)
    orderDTO = response.body()

    then: "Validate http status code CREATED"
    log.info("-----------------------------")
    log.info("---------------- RESPONSE: ${response.dump()} ------------------")
    log.info("----------------<Order DTO: ${orderDTO.dump()} ------------------")
    log.info("-----------------------------")
    response.status().code == HttpStatus.CREATED.code
    orderDTO.id
  }

  void "Should get one order"() {
    when: 'Get a order by id'
    log.info("Order to share ${orderDTO.toString()}")
    OrderDTO orderDTOFromEndPoint = client.toBlocking().retrieve(HttpRequest.GET("/api/orders/${orderDTO.id}").basicAuth("juan", "juan@2"),
    OrderDTO.class)

    then:"return order by id"
    log.info("-----------------------------")
    log.info("Order from endpont: ${orderDTOFromEndPoint.toString()}")
    log.info("-----------------------------")
    orderDTOFromEndPoint.id == orderDTO.id
  }

}
