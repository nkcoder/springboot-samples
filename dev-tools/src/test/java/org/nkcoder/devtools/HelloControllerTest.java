package org.nkcoder.devtools;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class HelloControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnMessage_whenHello() throws Exception {
    mockMvc.perform(get("/hello"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("message", is("world")));
  }

}
