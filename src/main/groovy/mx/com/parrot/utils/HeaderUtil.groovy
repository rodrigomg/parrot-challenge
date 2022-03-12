package mx.com.parrot.utils

import groovy.util.logging.Slf4j
import io.micronaut.http.MutableHttpHeaders

@Slf4j
final class HeaderUtil{

  private HeaderUtil() {}

  static void createAlert(MutableHttpHeaders headers, String applicationName, String message, String param) {
    headers.add("X-" + applicationName + "-alert", message)
    headers.add("X-" + applicationName + "-params", param)
  }

  static void createEntityCreationAlert(
    MutableHttpHeaders headers,
    String applicationName,
    boolean enableTranslation,
    String entityName,
    String param
  ) {
    String message = enableTranslation
    ? applicationName + "." + entityName + ".created"
    : "A new " + entityName + " is created with identifier " + param
    createAlert(headers, applicationName, message, param)
  }

  static void createEntityUpdateAlert(
    MutableHttpHeaders headers,
    String applicationName,
    boolean enableTranslation,
    String entityName,
    String param
  ) {
    String message = enableTranslation
    ? applicationName + "." + entityName + ".updated"
    : "A " + entityName + " is updated with identifier " + param
    createAlert(headers, applicationName, message, param)
  }

  static void createEntityDeletionAlert(
    MutableHttpHeaders headers,
    String applicationName,
    boolean enableTranslation,
    String entityName,
    String param
  ) {
    String message = enableTranslation
    ? applicationName + "." + entityName + ".deleted"
    : "A " + entityName + " is deleted with identifier " + param
    createAlert(headers, applicationName, message, param)
  }

  static void createFailureAlert(
    MutableHttpHeaders headers,
    String applicationName,
    boolean enableTranslation,
    String entityName,
    String errorKey,
    String defaultMessage
  ) {
    log.error("Entity processing failed, {}", defaultMessage)
    String message = enableTranslation ? "error." + errorKey : defaultMessage
    headers.add("X-" + applicationName + "-error", message)
    headers.add("X-" + applicationName + "-params", entityName)
  }
}
