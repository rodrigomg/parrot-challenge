package mx.com.parrot.service

import mx.com.parrot.domain.OrderDetail
import mx.com.parrot.service.dto.OrderDetailDTO
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

import java.util.Optional
/**
 * Service Interface for managing {@link mx.com.parrot.domain.OrderDetail}.
 */
interface OrderDetailService{
  /**
   * Save a order.
   *
   * @param order DTO the entity to save.
   * @return the persisted entity.
   */
  OrderDetailDTO save(OrderDetailDTO orderDetailDTO)

  /**
   * Get all the orders.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<OrderDetailDTO> findAll(Pageable pageable)

  /**
   * Get the "id" order.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<OrderDetailDTO> findOne(Long id)

  /**
   * Delete the "id" order.
   *
   * @param id the id of the entity.
   */
  void delete(Long id)

}
