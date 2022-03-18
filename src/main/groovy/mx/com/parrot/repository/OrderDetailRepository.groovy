package mx.com.parrot.repository

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import mx.com.parrot.domain.OrderDetail

@SuppressWarnings("unused")
@Repository
abstract class OrderDetailRepository implements JpaRepository<OrderDetail, Long>{

  @PersistenceContext
  EntityManager entityManager

  @Transactional
  OrderDetail mergeAndSave(OrderDetail orderDetail) {
    orderDetail = entityManager.merge(orderDetail)
    save(orderDetail)
  }
}
