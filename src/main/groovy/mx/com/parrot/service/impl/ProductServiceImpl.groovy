package mx.com.parrot.service.impl

import mx.com.parrot.service.dto.ProductDTO
import mx.com.parrot.domain.Product
import mx.com.parrot.service.mapper.ProductMapper
import groovy.util.logging.Slf4j
import mx.com.parrot.repository.ProductRepository
import mx.com.parrot.service.ProductService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.transaction.annotation.ReadOnly

import jakarta.inject.Singleton
import jakarta.inject.Inject
import javax.transaction.Transactional
import java.util.Optional

/**
 * Service Implementation for managing {@link Product}.
 */
@Slf4j
@Singleton
@Transactional
class ProductServiceImpl implements ProductService{

  @Inject
  ProductRepository productRepository
  @Inject
  ProductMapper productMapper

  /**
   * Save a product.
   *
   * @param product the entity to save.
   * @return the persisted entity.
   */
  @Override
  ProductDTO save(ProductDTO productDTO) {
    log.info("Request to save a Product : ${productDTO}")
    productMapper.toDto(productRepository.mergeAndSave(productMapper.toEntity(productDTO).get())).get()
  }

  /**
   * Get all the products.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Override
  @ReadOnly
  @Transactional
  Page<ProductDTO> findAll(Pageable pageable) {
    log.info("Request to get all Products")
    productMapper.toDtoList(productRepository.findAll(pageable).getContent())
  }

  /**
   * Get one product by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Override
  @ReadOnly
  @Transactional
  Optional<ProductDTO> findOne(Long id) {
    log.info("Request to get Product : ${id}")
    Optional<Product> optionalProduct = productRepository.findById(id)
    optionalProduct.isPresent() ? productMapper.toDto(optionalProduct.get()) : null
  }

  /**
   * Delete the product by id.
   *
   * @param id the id of the entity.
   */
  @Override
  void delete(Long id) {
    log.info("Request to delete Product : ${id}")
    productRepository.deleteById(id)
  }
}
