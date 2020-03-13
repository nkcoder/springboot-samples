package org.nkcoder.scala.repo

import java.lang.Long
import java.time.LocalDateTime

import javax.persistence._
import org.nkcoder.scala.api.Article

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Entity
@Table(name = "article")
class ArticleEntity(@BeanProperty var subject: String,
                    @BeanProperty var content: String,
                    @BeanProperty var updatedAt: LocalDateTime,
                    @BeanProperty var createdBy: Long) {

  @(Id @field)
  @BeanProperty
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = _

  // for jpa
  private def this() = this(null, null, null, null)

  def toArticle: Article =
    new Article(id, subject, content, createdBy, updatedAt)

}

object ArticleEntity {
  def create(subject: String, content: String, createdBy: Long): ArticleEntity =
    new ArticleEntity(subject, content, LocalDateTime.now(), createdBy)

}
