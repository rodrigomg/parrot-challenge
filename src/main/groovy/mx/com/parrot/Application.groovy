package mx.com.parrot

import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme

@OpenAPIDefinition(
  info = @Info(
    title = "parrot service",
    version = "0.1",
    description = "Parrot APIs"
  )
)
@SecurityScheme(
  name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic", description = "Input your username and password to access this API"
)
@CompileStatic
class Application {
  static void main(String[] args) {
    Micronaut.run(Application, args)
  }
}
