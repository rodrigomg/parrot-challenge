package mx.com.parrot.service

import mx.com.parrot.domain.Product
import mx.com.parrot.service.dto.ProductDTO
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

import java.util.Optional
/**
 * Service Interface for managing {@link mx.com.parrot.domain.Product}.
 */
interface ProductService{
  /**
   * Save a product.
   *
   * @param product DTO the entity to save.
   * @return the persisted entity.
   */
  ProductDTO save(ProductDTO productDTO)

  /**
   * Get all the products.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<ProductDTO> findAll(Pageable pageable)

  /**
   * Get the "id" product.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<ProductDTO> findOne(Long id)

  /**
   * Delete the "id" product.
   *
   * @param id the id of the entity.
   */
  void delete(Long id)

}
