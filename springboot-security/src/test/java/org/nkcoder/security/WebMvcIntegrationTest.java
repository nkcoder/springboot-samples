package org.nkcoder.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.security.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class WebMvcIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @WithMockUser(username = "kobe", password = "123456", authorities = {"USER"})
  @Test
  public void shouldSucceedWith200_whenGivenAuthenticatedUser() throws Exception {
    mockMvc.perform(get("/user/principal"))
        .andExpect(status().isOk());
  }

}
