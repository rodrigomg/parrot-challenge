package mx.com.parrot.repository

import mx.com.parrot.domain.User

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import javax.persistence.NoResultException

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

  @Transactional
  User findByEmail(String email){
  User user = new User()
    try {
          user = entityManager.createQuery("FROM User AS user WHERE user.email = :email", User)
          .setParameter("email", email).getSingleResult()
          user
    } catch (NoResultException) {
      user
    }
  }

}
