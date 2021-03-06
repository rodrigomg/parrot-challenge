package mx.com.parrot.service

import mx.com.parrot.service.dto.ProductDTO
import mx.com.parrot.service.mapper.ProductMapper
import groovy.util.logging.Slf4j
import mx.com.parrot.service.ProductService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import spock.lang.Shared
import jakarta.inject.Inject

@Slf4j
@MicronautTest(rollback = false)
class ProductServiceSpec extends Specification{

  @Inject
  ProductService productService
  @Inject
  ProductMapper productMapper

  @Shared
  ProductDTO productDTO

  void setupSpec(){
    productDTO = new ProductDTO()
  }

  void "should create a new product"() {
    when:
    productDTO.name = "Lamborghini"
    productDTO.price = 200.00
    productDTO.stock = 10
    productDTO = productService.save(productDTO)

    then:
    log.info("productDTO: ${productDTO.toString()}")
    productDTO.id
    productDTO.name == "Lamborghini"

  }

  void "Should get one product"() {
    when:
    ProductDTO productFromRepo = productService.findOne(productDTO.id).get()

    then:
    log.info("Product: ${productFromRepo.toString()}")
    productFromRepo.id
    productFromRepo.name == "Lamborghini"
  }

  void "Should get one product by name"() {
    when:
    ProductDTO productFromRepo = productService.findByName("Lamborghini").get()

    then:
    log.info("Product from name: ${productFromRepo.toString()}")
    productFromRepo.id
    productFromRepo.name == "Lamborghini"
  }
}
