package mx.com.parrot.service

import mx.com.parrot.service.dto.OrderDTO
import mx.com.parrot.service.mapper.OrderMapper
import mx.com.parrot.service.mapper.UserMapper
import groovy.util.logging.Slf4j
import mx.com.parrot.service.OrderService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import spock.lang.Shared
import spock.lang.Ignore
import jakarta.inject.Inject
import mx.com.parrot.service.UserService
import mx.com.parrot.service.ProductService
import mx.com.parrot.service.dto.UserDTO
import mx.com.parrot.service.dto.OrderDetailDTO
import mx.com.parrot.service.dto.ProductDTO
import java.lang.Long

@Slf4j
@MicronautTest(rollback = true)
class OrderServiceSpec extends Specification{

  @Inject
  OrderService orderService
  @Inject
  UserService userService
  @Inject
  ProductService productService
  @Inject
  UserMapper userMapper

  @Shared
  OrderDTO orderDTO

  void setupSpec(){
    orderDTO = new OrderDTO()
  }

  void "should create a new order"() {
    when:
    UserDTO userDTO = userService.findOne(1L).get()
    orderDTO.userDTO = userDTO
    Set<OrderDetailDTO> orderDetailsDTO = new HashSet<OrderDetailDTO>()
    for(int i = 1 ; i < 3 ; i++){
      OrderDetailDTO orderDetailDTO = new OrderDetailDTO()
      ProductDTO productDTO = productService.findOne(i).get()
      orderDetailDTO.productDTO = productDTO
      orderDetailsDTO.add(orderDetailDTO)
    }
    OrderDetailDTO orderDetailDTO = new OrderDetailDTO()
    ProductDTO productDTO = new ProductDTO(name: "pan", price: 5.50, stock: 20)
    orderDetailDTO.productDTO = productDTO
    orderDTO.orderDetailsDTO = orderDetailsDTO
    orderDetailsDTO.add(orderDetailDTO)
    orderDTO = orderService.save(orderDTO)

    then:
    log.info("orderDTO: ${orderDTO.toString()}")
    orderDTO.id
    orderDTO.userDTO.email == "roger.mtz@gmail.com"
    orderDTO.orderDetailsDTO.size() == 3
  }

  @Ignore
  void "Should get one order"() {
    when:
    OrderDTO orderFromRepo = orderService.findOne(orderDTO.id).get()

    then:
    log.info("User: ${orderFromRepo.toString()}")
    orderFromRepo.id
    orderFromRepo.userDTO.email == "roger.mtz@gmail.com"
  }

  @Ignore
  void "Should get order id 2"() {
    when:
    OrderDTO orderFromRepo = orderService.findOne(2L).get()

    then:
    log.info("Order: ${orderFromRepo.dump()}")
    orderFromRepo.id
    orderFromRepo.userDTO.email == "roger.mtz@gmail.com"
    orderFromRepo.orderDetailsDTO.size() == 2
  }

}
