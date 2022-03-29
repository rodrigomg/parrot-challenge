package mx.com.parrot.repository

import mx.com.parrot.domain.Product
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import javax.persistence.NoResultException

@SuppressWarnings("unused")
@Repository
abstract class ProductRepository implements JpaRepository<Product, Long>{

  @PersistenceContext
  EntityManager entityManager

  @Transactional
  Product mergeAndSave(Product product) {
    product = entityManager.merge(product)
    save(product)
  }

  @Transactional
  Product findByName(String name){
    Product product = new Product()
    try {
          product = entityManager.createQuery("FROM Product AS product WHERE product.name = :name", Product)
          .setParameter("name", name).getSingleResult()
          product
    } catch (NoResultException ex) {
      product
    }
  }
}
