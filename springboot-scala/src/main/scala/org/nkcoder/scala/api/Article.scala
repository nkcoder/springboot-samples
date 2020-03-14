package org.nkcoder.scala.api

import java.time.LocalDateTime

import scala.beans.BeanProperty

class Article(@BeanProperty var id: Long,
              @BeanProperty var subject: String,
              @BeanProperty var content: String,
              @BeanProperty var createdBy: Long,
              @BeanProperty var updatedBy: LocalDateTime)
