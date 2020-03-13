package org.nkcoder.scala.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait ArticleRepository extends JpaRepository[ArticleEntity, Long] {}
