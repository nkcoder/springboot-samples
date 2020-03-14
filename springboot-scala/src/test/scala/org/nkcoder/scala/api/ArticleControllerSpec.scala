package org.nkcoder.scala.api
import java.time.LocalDateTime

import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.RestAssuredMockMvc._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.{when => mockWhen, _}
import org.nkcoder.scala.UnitSpec
import org.nkcoder.scala.service.ArticleService
import org.springframework.http.HttpStatus

class ArticleControllerSpec extends UnitSpec {

  private val articleService = mock(classOf[ArticleService])
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
      .get("/articles/" + id)
      .Then()
      .status(HttpStatus.OK)
  }

  it should "delete article by id" in {
    val id = 1L
    doNothing().when(articleService).deleteById(id)

    given()
      .delete("/articles/{id}", id)
      .Then()
      .status(HttpStatus.OK)
  }

  it should "find all articles" in {
    val (pageNum, pageSize) = (1, 10)
    val pageData = new PageData[Article](
      List(new Article(1L, "s", "c", 1L, LocalDateTime.now())),
      1
    )

    mockWhen(articleService.getAll(pageNum, pageSize)).thenReturn(pageData)

    given()
      .queryParam("pageNum", pageNum)
      .queryParam("pageSize", pageSize)
      .when()
      .get("/articles")
      .Then()
      .status(HttpStatus.OK)
      .log()
      .all()
      .body("total", equalTo(1))

  }

  it should "update article" in {
    val id = 1
    val updateArticleRequest =
      new CreateOrUpdateArticleRequest("s11", "c11", 3L)

    doNothing().when(articleService).update(id, updateArticleRequest)

    given()
      .contentType(ContentType.JSON)
      .body(updateArticleRequest)
      .when()
      .put("/articles/{id}", id)
      .`then`()
      .status(HttpStatus.OK)

  }

}
