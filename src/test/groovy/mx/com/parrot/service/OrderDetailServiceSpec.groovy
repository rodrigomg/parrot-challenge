package mx.com.parrot.service

import mx.com.parrot.service.dto.OrderDetailDTO
import mx.com.parrot.service.mapper.OrderDetailMapper
import groovy.util.logging.Slf4j
import mx.com.parrot.service.OrderDetailService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import spock.lang.Shared
import spock.lang.Ignore
import jakarta.inject.Inject

@Slf4j
@MicronautTest(rollback = false)
class OrderDetailServiceSpec extends Specification{

  @Inject
  OrderDetailService orderDetailService
  @Inject
  OrderService orderService
  @Inject
  ProductService productService


  @Shared
  OrderDetailDTO orderDetailDTO

  void setupSpec(){
    orderDetailDTO = new OrderDetailDTO()
  }

  void "should create a new order detail"() {
    when:
    orderDetailDTO.orderId = orderService.findOne(1L).get().id
    orderDetailDTO.productDTO = productService.findOne(1L).get()
    orderDetailDTO = orderDetailService.save(orderDetailDTO)

    then:
    log.info("orderDetailDTO: ${orderDetailDTO.toString()}")
    orderDetailDTO.id
    orderDetailDTO.productDTO.name == "carne"
  }

  void "Should get one order detail"() {
    when:
    OrderDetailDTO orderDetailFromRepo = orderDetailService.findOne(orderDetailDTO.id).get()

    then:
    log.info("Order detail: ${orderDetailFromRepo.toString()}")
    orderDetailFromRepo.id
    orderDetailDTO.productDTO.name == "carne"
  }

}
