package org.nkcoder.scala.repo

import org.assertj.core.api.Assertions._
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataJpaTest
@ExtendWith(Array(classOf[SpringExtension]))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ArticleRepositorySpec {

  @Autowired private val articleRepository: ArticleRepository = null

  @Test
  def shouldSaveArticle(): Unit = {
    val articleEntity = ArticleEntity.create("sf", "coon", 1L)
    val articleSaved = articleRepository.save(articleEntity)

    assertThat(articleSaved).isNotNull
    assertThat(articleSaved.subject).isEqualTo(articleEntity.subject)
  }

  @Test
  def shouldFindById(): Unit = {
    val articleEntity = ArticleEntity.create("sf", "coon", 1L)
    val articleSaved = articleRepository.save(articleEntity)

    val articleById = articleRepository.findById(articleSaved.id)

    assertThat(articleById).isPresent
    assertThat(articleById.get()).isEqualTo(articleSaved)

  }

  @Test
  def shouldDeleteById(): Unit = {
    val articleEntity = ArticleEntity.create("sf", "coon", 1L)
    val articleSaved = articleRepository.save(articleEntity)

    articleRepository.deleteById(articleSaved.id)

  }

  @Test
  def shouldFindAll(): Unit = {
    import scala.jdk.CollectionConverters._
    val articles = List(
      ArticleEntity.create("s1", "c1", 1L),
      ArticleEntity.create("s2", "c2", 2L)
    )
    articleRepository.saveAll(articles.asJava)

    val allArticles = articleRepository.findAll(PageRequest.of(0, 10))
    assertThat(allArticles.getTotalElements)
      .isGreaterThanOrEqualTo(articles.size)
  }
}
