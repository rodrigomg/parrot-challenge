package mx.com.parrot.service.impl

import mx.com.parrot.service.dto.UserDTO
import mx.com.parrot.domain.User
import mx.com.parrot.service.mapper.UserMapper
import groovy.util.logging.Slf4j
import mx.com.parrot.repository.UserRepository
import mx.com.parrot.service.UserService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.transaction.annotation.ReadOnly

import jakarta.inject.Singleton
import jakarta.inject.Inject
import javax.transaction.Transactional
import java.util.Optional

/**
 * Service Implementation for managing {@link User}.
 */
@Slf4j
@Singleton
@Transactional
class UserServiceImpl implements UserService{

  @Inject
  UserRepository userRepository
  @Inject
  UserMapper userMapper

  /**
   * Save a user.
   *
   * @param user the entity to save.
   * @return the persisted entity.
   */
  @Override
  UserDTO save(UserDTO userDTO) {
    log.info("Request to save a User : ${userDTO}")
    userMapper.toDto(userRepository.mergeAndSave(userMapper.toEntity(userDTO).get())).get()
  }

  /**
   * Get all the users.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Override
  @ReadOnly
  @Transactional
  Page<UserDTO> findAll(Pageable pageable) {
    log.info("Request to get all Users")
    userMapper.toDtoList(userRepository.findAll(pageable).getContent())
  }

  /**
   * Get one user by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Override
  @ReadOnly
  @Transactional
  Optional<UserDTO> findOne(Long id) {
    log.info("Request to get User : ${id}")
    Optional<User> optionalUser = userRepository.findById(id)
    optionalUser.isPresent() ? userMapper.toDto(optionalUser.get()) : null
  }

  /**
   * Delete the user by id.
   *
   * @param id the id of the entity.
   */
  @Override
  void delete(Long id) {
    log.info("Request to delete User : ${id}")
    userRepository.deleteById(id)
  }
}
