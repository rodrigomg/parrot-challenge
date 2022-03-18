package mx.com.parrot.repository

import mx.com.parrot.domain.OrderDetail
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
class OrderDetailRepositorySpec extends Specification{

  @Inject
  OrderDetailRepository orderDetailRepository
  @Inject
  OrderRepository orderRepository
  @Inject
  ProductRepository productRepository

  @Shared
  OrderDetail orderDetail

  void setupSpec(){
    orderDetail = new OrderDetail()
  }

  void "Should create a new order"() {
    when:
    orderDetail.order = orderRepository.findById(1L).get()
    orderDetail.product = productRepository.findById(1L).get()
    log.info("OrderDetail: ${orderDetail.toString()}")
    orderDetail = orderDetailRepository.mergeAndSave(orderDetail)

    then:
    log.info("OrderDetail: ${orderDetail.toString()}")
    orderDetail.id
    orderDetail.product.name == "carne"
  }

  void "Should get one order"() {
    when:
    OrderDetail orderDetailFromRepo = orderDetailRepository.findById(orderDetail.id).get()

    then:
    log.info("OrderDetail: ${orderDetailFromRepo.toString()}")
    orderDetailFromRepo.id
    orderDetailFromRepo.product.name == "carne"
  }

  void "Should get all producs"() {
    when:
    List<OrderDetail> orderDetails = orderDetailRepository.findAll(Pageable.from(0,10)).getContent()

    then:
    log.info("OrderDetails: ${orderDetails.dump()}")
    orderDetails.size() == 4
  }

  void "Should delete an order"() {
    when:
    orderDetailRepository.deleteById(orderDetail.id)

    then:
    !orderDetailRepository.findById(orderDetail.id).isPresent()
  }

}
