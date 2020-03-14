package org.nkcoder.scala.api

import io.restassured.RestAssured
import io.restassured.RestAssured._
import io.restassured.http.ContentType
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.{BeforeEach, Test}
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ArticleIntegrationTest {

  @LocalServerPort
  private var port: Int = _

  @BeforeEach
  def setup(): Unit = {
    RestAssured.port = port
  }

  @Test
  def shouldCreateArticle(): Unit = {
    val article = new CreateOrUpdateArticleRequest("sub", "con", 1L)
    given()
      .contentType(ContentType.JSON)
      .body(article)
      .when()
      .post("/api/articles")
      .`then`()
      .statusCode(HttpStatus.CREATED.value())
  }

}
