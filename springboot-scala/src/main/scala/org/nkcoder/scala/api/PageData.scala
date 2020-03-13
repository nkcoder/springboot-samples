package org.nkcoder.scala.api

@SerialVersionUID(2)
case class PageData[T](data: List[T], total: Long) extends Serializable
