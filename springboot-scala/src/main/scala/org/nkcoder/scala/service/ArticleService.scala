package org.nkcoder.scala.service

import org.nkcoder.scala.api.{Article, CreateOrUpdateArticleRequest, PageData}

trait ArticleService {
  def getAll(pageNum: Int, pageSize: Int): PageData[Article]

  def getById(id: Long): Article

  def update(id: Long, updateArticleRequest: CreateOrUpdateArticleRequest): Unit

  def deleteById(id: Long): Unit

  def create(createArticleRequest: CreateOrUpdateArticleRequest): Long

}
