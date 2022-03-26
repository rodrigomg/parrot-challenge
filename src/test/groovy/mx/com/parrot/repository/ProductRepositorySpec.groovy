package mx.com.parrot.repository

import mx.com.parrot.domain.Product
import groovy.util.logging.Slf4j
import io.micronaut.data.model.Pageable
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise


@Slf4j
@MicronautTest( rollback = false)
@Stepwise
class ProductRepositorySpec extends Specification{

  @Inject
  ProductRepository productRepository

  @Shared
  Product product

  void setupSpec(){
    product = new Product()
  }

  void "Should create a new product"() {
    when:
    product.name = "Ferrari"
    product.price = 100.00
    product.stock = 10
    product = productRepository.mergeAndSave(product)

    then:
    log.info("Product: ${product.toString()}")
    product.id
    product.name == "Ferrari"
  }

  void "Should get one product"() {
    when:
    Product productFromRepo = productRepository.findById(product.id).get()

    then:
    log.info("Product: ${productFromRepo.toString()}")
    productFromRepo.id
    productFromRepo.name == "Ferrari"
  }

  void "Should get one product by name"() {
    when:
    Product productFromRepo = productRepository.findByName("Ferrari")

    then:
    log.info("Product by name: ${productFromRepo.toString()}")
    productFromRepo.id
    productFromRepo.name == "Ferrari"
  }

  void "Should get all producs"() {
    when:
    List<Product> products = productRepository.findAll(Pageable.from(0,10)).getContent()

    then:
    log.info("Products: ${products.dump()}")
    products.size() == 4
  }

  void "Should update a product"() {
    when:
    product.price = 300.00
    product = productRepository.update(product)

    then:
    log.info("Product: ${product.toString()}")
    product.id
    product.price == 300.00
  }

  void "Should delete an product"() {
    when:
    productRepository.deleteById(product.id)

    then:
    !productRepository.findById(product.id).isPresent()
  }

}
