package mx.com.parrot.repository

import mx.com.parrot.domain.Order
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
class OrderRepositorySpec extends Specification{

  @Inject
  OrderRepository orderRepository
  @Inject
  UserRepository userRepository

  @Shared
  Order order

  void setupSpec(){
    order = new Order()
  }

  void "Should create a new order"() {
    when:
    order.user = userRepository.findById(1L).get()
    order = orderRepository.mergeAndSave(order)

    then:
    log.info("Order: ${order.toString()}")
    order.id
    order.user.email == "roger.mtz@gmail.com"
  }

  void "Should get one order"() {
    when:
    Order orderFromRepo = orderRepository.findById(order.id).get()

    then:
    log.info("Order: ${orderFromRepo.toString()}")
    orderFromRepo.id
    orderFromRepo.user.email == "roger.mtz@gmail.com"
  }

  void "Should get all producs"() {
    when:
    List<Order> orders = orderRepository.findAll(Pageable.from(0,10)).getContent()

    then:
    log.info("Orders: ${orders.dump()}")
    orders.size() == 1
  }

  void "Should delete an order"() {
    when:
    orderRepository.deleteById(order.id)

    then:
    !orderRepository.findById(order.id).isPresent()
  }

}
