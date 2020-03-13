package org.nkcoder.scala.api

import com.typesafe.scalalogging.Logger
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation
import org.springframework.web.bind.annotation.{
  ControllerAdvice,
  ResponseStatus
}

@ControllerAdvice
class ExceptionHandler {

  private val logger: Logger = Logger(classOf[ExceptionHandler])

  @annotation.ExceptionHandler(Array(classOf[ArticleNotFoundException]))
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def handleArticleNotFound(
    articleNotFoundException: ArticleNotFoundException
  ): ResponseEntity[String] = {
    logger.error(s"exception occurred: $articleNotFoundException")
    new ResponseEntity[String](
      articleNotFoundException.message,
      HttpStatus.NOT_FOUND
    )
  }
}

abstract class AppException(message: String, cause: Throwable)
    extends RuntimeException(message, cause)

case class ArticleNotFoundException(message: String, cause: Throwable = null)
    extends AppException(message, cause)
