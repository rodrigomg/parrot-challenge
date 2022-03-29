package mx.com.parrot.service.impl

import mx.com.parrot.service.dto.OrderDTO
import mx.com.parrot.service.dto.UserDTO
import mx.com.parrot.service.dto.ProductDTO
import mx.com.parrot.service.dto.OrderDetailDTO
import mx.com.parrot.domain.Order
import mx.com.parrot.service.mapper.OrderMapper
import mx.com.parrot.service.mapper.ProductMapper
import groovy.util.logging.Slf4j
import mx.com.parrot.repository.OrderRepository
import mx.com.parrot.service.OrderService
import mx.com.parrot.service.UserService
import mx.com.parrot.service.ProductService
import mx.com.parrot.service.OrderDetailService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.transaction.annotation.ReadOnly

import jakarta.inject.Singleton
import jakarta.inject.Inject
import javax.transaction.Transactional
import java.util.Optional
import java.util.HashSet
import mx.com.parrot.service.dto.OrderDetailDTO
import mx.com.parrot.domain.Product

/**
 * Service Implementation for managing {@link Order}.
 */
@Slf4j
@Singleton
@Transactional
class OrderServiceImpl implements OrderService{

  @Inject
  OrderRepository orderRepository
  @Inject
  UserService userService
  @Inject
  ProductService productService
  @Inject
  OrderDetailService orderDetailService

  @Inject
  OrderMapper orderMapper
  @Inject
  ProductMapper productMapper
  /**
   * Save a order.
   *
   * @param orderDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  OrderDTO save(OrderDTO orderDTO) {
    log.info("Request to save a Order : ${orderDTO}")
    Optional<UserDTO> userDTO = userService.findByEmail(orderDTO.userDTO.email)
    orderDTO.userDTO = userDTO.get()
    Set<OrderDetailDTO> orderDetailsDTO = orderDTO.orderDetailsDTO
    orderDTO.orderDetailsDTO = []
    Order order = orderRepository.mergeAndSave(orderMapper.toEntity(orderDTO).get())
    orderDetailsDTO.collect { orderDetail ->
      orderDetail.orderId = order.id
      Optional<ProductDTO> oProductDTO = productService.findByName(orderDetail.productDTO.name)

      if(oProductDTO.isPresent() && oProductDTO.get().id){
        orderDetail.productDTO = oProductDTO.get()
      }else{
        orderDetail.productDTO = productService.save(orderDetail.productDTO)
      }

    }
    orderDTO.orderDetailsDTO = orderDetailsDTO
    log.info("Order DTO: ${orderDTO.dump()}")
    orderMapper.toDto(order).get()
  }

  /**
   * Get all the orders.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Override
  @ReadOnly
  @Transactional
  Page<OrderDTO> findAll(Pageable pageable) {
    log.info("Request to get all Orders")
    orderMapper.toDtoList(orderRepository.findAll(pageable).getContent())
  }

  /**
   * Get one order by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Override
  @ReadOnly
  @Transactional
  Optional<OrderDTO> findOne(Long id) {
    log.info("Request to get Order : ${id}")
    Optional<Order> optionalOrder = orderRepository.findById(id)
    optionalOrder.isPresent() ? orderMapper.toDto(optionalOrder.get()) : null
  }

  /**
   * Delete the order by id.
   *
   * @param id the id of the entity.
   */
  @Override
  void delete(Long id) {
    log.info("Request to delete Order : ${id}")
    orderRepository.deleteById(id)
  }
}
