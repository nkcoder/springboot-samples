package org.nkcoder.scala

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@SpringBootApplication
class SpringBootScalaApplication {

  @Bean
  def jacksonBuilder: Jackson2ObjectMapperBuilder = {
    val mapperBuilder: Jackson2ObjectMapperBuilder =
      new Jackson2ObjectMapperBuilder

    mapperBuilder.modules(DefaultScalaModule, new JavaTimeModule)
    mapperBuilder.featuresToDisable(
      SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
    )

    mapperBuilder
  }

}

object SpringBootScalaApplication {
  def main(args: Array[String]): Unit =
    SpringApplication.run(classOf[SpringBootScalaApplication], args: _*)
}
