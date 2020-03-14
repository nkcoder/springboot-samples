package org.nkcoder.scala.api
import java.time.LocalDateTime

import io.restassured.module.mockmvc.RestAssuredMockMvc._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._
import org.mockito.Mockito.{when => mockWhen, _}
import org.nkcoder.scala.UnitSpec
import org.nkcoder.scala.service.ArticleService
import org.scalatest.Ignore
import org.springframework.http.HttpStatus

/**
* todo
  */
@Ignore
class ArticleControllerSpec extends UnitSpec {

  val articleService = mock(classOf[ArticleService])

  "ArticleController" should "create article" in {
    val createArticleRequest =
      new CreateOrUpdateArticleRequest("sub", "con", 1L)
    val id = 1L
    val jsonAsMap =
      Map("subject" -> "sub", "content" -> "con", "createdBy" -> 1L)

    mockWhen(articleService.create(createArticleRequest)).thenReturn(id)

    given()
      .standaloneSetup(new ArticleController(articleService))
//      .contentType("application/json")
      .contentType("application/json")
      .body(jsonAsMap)
//      .body(createArticleRequest)
      .post("/articles")
      .Then()
      .body("$", equalTo(id))
  }

  it should "get article by id" in {
    val id = 1L
    val article = new Article(1, "sub", "con", 1L, LocalDateTime.now())
    mockWhen(articleService.getById(id)).thenReturn(article)

    given()
      .standaloneSetup(new ArticleController(articleService))
      .get("/articles/" + id)
      .Then()
      .status(HttpStatus.OK)
  }

}
