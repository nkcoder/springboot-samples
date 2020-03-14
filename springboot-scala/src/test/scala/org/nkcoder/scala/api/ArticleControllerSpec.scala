package org.nkcoder.scala.api
import java.time.LocalDateTime

import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.RestAssuredMockMvc._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.{when => mockWhen, _}
import org.nkcoder.scala.UnitSpec
import org.nkcoder.scala.service.ArticleService
import org.springframework.http.HttpStatus

class ArticleControllerSpec extends UnitSpec {

  val articleService = mock(classOf[ArticleService])
  RestAssuredMockMvc.standaloneSetup(new ArticleController(articleService))

  "ArticleController" should "create article" in {
    val createArticleRequest =
      new CreateOrUpdateArticleRequest("sub", "con", 1L)
    val id = 1L

    mockWhen(
      articleService
        .create(ArgumentMatchers.any(classOf[CreateOrUpdateArticleRequest]))
    ).thenReturn(id)

    given()
      .contentType("application/json")
      .body(createArticleRequest)
      .post("/articles")
      .Then()
      .status(HttpStatus.CREATED)
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
