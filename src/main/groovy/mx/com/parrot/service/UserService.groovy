package mx.com.parrot.service

import mx.com.parrot.domain.User
import mx.com.parrot.service.dto.UserDTO
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

import java.util.Optional
/**
 * Service Interface for managing {@link mx.com.parrot.domain.User}.
 */
interface UserService{
  /**
   * Save an user.
   *
   * @param userDTO the entity to save.
   * @return the persisted entity.
   */
  UserDTO save(UserDTO userDTO)

  /**
   * Get all the users.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<UserDTO> findAll(Pageable pageable)


  /**
   * Get the "id" user.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<UserDTO> findOne(Long id)

  /**
   * Get the "email" user.
   *
   * @param email the id of the entity.
   * @return the entity.
   */
  Optional<UserDTO> findByEmail(String email)

  /**
   * Delete the "id" user.
   *
   * @param id the id of the entity.
   */
  void delete(Long id)

}
