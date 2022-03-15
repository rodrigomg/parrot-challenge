package mx.com.parrot.repository

import mx.com.parrot.domain.Order
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@SuppressWarnings("unused")
@Repository
abstract class OrderRepository implements JpaRepository<Order, Long>{

  @PersistenceContext
  EntityManager entityManager

  @Transactional
  Order mergeAndSave(Order order) {
    order = entityManager.merge(order)
    save(order)
  }
}
