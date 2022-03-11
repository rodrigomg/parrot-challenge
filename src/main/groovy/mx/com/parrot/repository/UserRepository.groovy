package mx.com.parrot.repository

import mx.com.parrot.domain.User

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@SuppressWarnings("unused")
@Repository
abstract class UserRepository implements JpaRepository<User, Long> {

  @PersistenceContext
  EntityManager entityManager

  @Transactional
  User mergeAndSave(User user) {
    user = entityManager.merge(user)
    save(user)
  }

}
