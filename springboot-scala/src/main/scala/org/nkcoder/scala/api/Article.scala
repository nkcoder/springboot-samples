package org.nkcoder.scala.api

import java.time.LocalDateTime

class Article(val id: Long,
              val subject: String,
              val content: String,
              val createdBy: Long,
              val updatedBy: LocalDateTime)
