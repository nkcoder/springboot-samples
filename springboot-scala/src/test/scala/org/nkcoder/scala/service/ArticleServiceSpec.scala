package org.nkcoder.scala.service

import java.time.LocalDateTime
import java.util.Optional

import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.nkcoder.scala.UnitSpec
import org.nkcoder.scala.api.{
  ArticleNotFoundException,
  CreateOrUpdateArticleRequest
}
import org.nkcoder.scala.repo.{ArticleEntity, ArticleRepository}
import org.springframework.data.domain.{PageImpl, PageRequest}

import scala.jdk.CollectionConverters._

class ArticleServiceSpec extends UnitSpec {

  private val articleRepository = mock(classOf[ArticleRepository])

  private val articleService = new ArticleServiceImpl(articleRepository)

  "ArticleService" should "create article" in {
    val createArticleRequest =
      new CreateOrUpdateArticleRequest("fs", "cont", 1L)
    val mockArticleEntity =
      new ArticleEntity("sfa", "conrs", LocalDateTime.now(), 2L)
    mockArticleEntity.id = 1L

    when(articleRepository.save(any())).thenReturn(mockArticleEntity)

    val id = articleService.create(createArticleRequest)

    id shouldEqual mockArticleEntity.id
  }

  it should "get all articles" in {
    val pageRequest = PageRequest.of(0, 10)
    val mockArticles =
      new PageImpl(List(ArticleEntity.create("dfas", "con", 1L)).asJava)

    when(articleRepository.findAll(pageRequest)).thenReturn(mockArticles)

    val articlesFound = articleService.getAll(1, 10)

    articlesFound should not be null
    articlesFound.total shouldBe 1

  }

  it should "throw ArticleNotFoundException when article not exist" in {
    val id = 1
    when(articleRepository.findById(id)).thenReturn(Optional.empty())

    assertThrows[ArticleNotFoundException](articleService.getById(id))
  }

  it should "get article by id" in {
    val id = 1L
    val mockArticleEntity = ArticleEntity.create("daf", "con", 1L)
    mockArticleEntity.id = id

    when(articleRepository.findById(id))
      .thenReturn(Optional.of(mockArticleEntity))

    val article = articleService.getById(id)

    article should not be null
    article.id shouldEqual id
  }

  it should "throw exception when article not found" in {
    val id = 1L
    when(articleRepository.deleteById(id))
      .thenThrow(ArticleNotFoundException("article not found"))

    assertThrows[ArticleNotFoundException](articleService.deleteById(id))
  }

  it should "delete article by id" in {
    val id = 1L
    doNothing().when(articleRepository).deleteById(id)

    articleService.deleteById(id)
  }

}
