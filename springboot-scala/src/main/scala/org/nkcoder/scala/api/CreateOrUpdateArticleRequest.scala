package org.nkcoder.scala.api
import java.lang.Long

@SerialVersionUID(1)
class CreateOrUpdateArticleRequest(val subject: String,
                                   val content: String,
                                   val createdBy: Long)
    extends Serializable
