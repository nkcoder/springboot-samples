package org.nkcoder.scala.service

import java.time.LocalDateTime

import org.nkcoder.scala.api.{
  Article,
  ArticleNotFoundException,
  CreateOrUpdateArticleRequest,
  PageData
}
import org.nkcoder.scala.repo.{ArticleEntity, ArticleRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters._

@Service
class ArticleServiceImpl @Autowired()(articleRepository: ArticleRepository)
    extends ArticleService {

  override def create(
    createArticleRequest: CreateOrUpdateArticleRequest
  ): Long = {
    import createArticleRequest._
    articleRepository
      .save(
        ArticleEntity
          .create(subject = subject, content = content, createdBy = createdBy)
      )
      .id
  }

  override def getAll(pageNum: Int, pageSize: Int): PageData[Article] = {
    // I don't know why this does not work
//    articleRepository.findAll(PageRequest.of(pageNum-1, pageSize)).map(_.toArticle)

    val articlePage =
      articleRepository.findAll(PageRequest.of(pageNum - 1, pageSize))

    val articles = articlePage.getContent.asScala.map(_.toArticle)

    PageData(articles.toList, articlePage.getTotalElements)
  }

  override def getById(id: Long): Article =
    articleRepository
      .findById(id)
      .map(_.toArticle)
      .orElseThrow(() => ArticleNotFoundException("article not found by id."))

  override def update(
    id: Long,
    updateArticleRequest: CreateOrUpdateArticleRequest
  ): Unit = {
    import updateArticleRequest._

    articleRepository
      .findById(id)
      .map(article => {
        article.subject = subject
        article.content = content
        article.updatedAt = LocalDateTime.now()
        article
      })
      .ifPresent(articleRepository.save(_))

  }

  override def deleteById(id: Long): Unit = {
    articleRepository.deleteById(id)
  }
}
