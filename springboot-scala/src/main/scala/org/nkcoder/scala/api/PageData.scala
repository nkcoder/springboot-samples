package org.nkcoder.scala.api

import scala.beans.BeanProperty

@SerialVersionUID(2)
case class PageData[T](@BeanProperty data: List[T], @BeanProperty total: Long)
    extends Serializable
