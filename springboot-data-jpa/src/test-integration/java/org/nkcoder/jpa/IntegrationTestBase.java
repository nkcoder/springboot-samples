package org.nkcoder.jpa;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.jpa.configuration.Mariadb4jExtension2;
import org.nkcoder.jpa.configuration.SpringApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({SpringExtension.class, Mariadb4jExtension2.class})
@ActiveProfiles({"test", "integration-test"})
@SpringBootTest
@Slf4j
public abstract class IntegrationTestBase {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SpringApplicationContext springApplicationContext;

    protected MockMvc mockMvc;

    protected MockMvcRequestSpecification given() {
        return RestAssuredMockMvc
            .given()
            .header("Accept", ContentType.JSON.withCharset(StandardCharsets.UTF_8))
            .header("Content-Type", ContentType.JSON.withCharset(StandardCharsets.UTF_8));
    }

    @BeforeEach
    protected void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        springApplicationContext.setApplicationContext(webApplicationContext);
    }

}
