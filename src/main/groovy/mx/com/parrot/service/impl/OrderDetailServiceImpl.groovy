package mx.com.parrot.service.impl

import mx.com.parrot.service.dto.OrderDetailDTO
import mx.com.parrot.domain.OrderDetail
import mx.com.parrot.service.mapper.OrderDetailMapper
import groovy.util.logging.Slf4j
import mx.com.parrot.repository.OrderDetailRepository
import mx.com.parrot.service.OrderDetailService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.transaction.annotation.ReadOnly

import jakarta.inject.Singleton
import jakarta.inject.Inject
import javax.transaction.Transactional
import java.util.Optional

/**
 * Service Implementation for managing {@link OrderDetail}.
 */
@Slf4j
@Singleton
@Transactional
class OrderDetailServiceImpl implements OrderDetailService{

  @Inject
  OrderDetailRepository orderDetailRepository
  @Inject
  OrderDetailMapper orderDetailMapper

  /**
   * Save a order.
   *
   * @param orderDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  OrderDetailDTO save(OrderDetailDTO orderDTO) {
    log.info("Request to save a OrderDetail : ${orderDTO}")
    orderDetailMapper.toDto(orderDetailRepository.mergeAndSave(orderDetailMapper.toEntity(orderDTO).get())).get()
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
  Page<OrderDetailDTO> findAll(Pageable pageable) {
    log.info("Request to get all OrderDetails")
    orderDetailMapper.toDtoList(orderDetailRepository.findAll(pageable).getContent())
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
  Optional<OrderDetailDTO> findOne(Long id) {
    log.info("Request to get OrderDetail : ${id}")
    Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(id)
    optionalOrderDetail.isPresent() ? orderDetailMapper.toDto(optionalOrderDetail.get()) : null
  }

  /**
   * Delete the order by id.
   *
   * @param id the id of the entity.
   */
  @Override
  void delete(Long id) {
    log.info("Request to delete OrderDetail : ${id}")
    orderDetailRepository.deleteById(id)
  }
}
