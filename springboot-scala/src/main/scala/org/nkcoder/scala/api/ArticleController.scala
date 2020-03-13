package org.nkcoder.scala.api

import org.nkcoder.scala.service.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/articles"))
class ArticleController @Autowired()(articleService: ArticleService) {

  @PostMapping(Array(""))
  @ResponseStatus(HttpStatus.CREATED)
  def create(
    @RequestBody createArticleRequest: CreateOrUpdateArticleRequest
  ): Long =
    articleService.create(createArticleRequest)

  @DeleteMapping(Array("/{id}"))
  def delete(@PathVariable("id") id: Long): Unit = articleService.deleteById(id)

  @PutMapping(Array("/{id}"))
  def update(
    @PathVariable("id") id: Long,
    @RequestBody updateArticleRequest: CreateOrUpdateArticleRequest
  ): Unit =
    articleService.update(id, updateArticleRequest)

  @GetMapping(Array("/{id}"))
  def get(@PathVariable("id") id: Long): Article = articleService.getById(id)

  @GetMapping(Array(""))
  def getAll(
    @RequestParam(value = "pageNum", defaultValue = "1") pageNum: Int,
    @RequestParam(value = "pageSize", defaultValue = "10") pageSize: Int
  ): PageData[Article] =
    articleService.getAll(pageNum, pageSize)

}
