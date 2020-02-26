package org.nkcoder.validation.config;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilder mapperBuilder() {
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
    builder.modules(new JavaTimeModule());
    builder.modules(new ParameterNamesModule(Mode.PROPERTIES));
    return builder;
  }

}
