package org.nkcoder.jpa.configuration;

import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Mariadb4jExtension2 implements BeforeAllCallback, AfterAllCallback {

    private static final int DEFAULT_PORT = 60000;
    private static MariaDB4jSpringService mariaDB4jSpringService;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (mariaDB4jSpringService != null) {
            return;
        }

        mariaDB4jSpringService = new MariaDB4jSpringService();

        DBConfigurationBuilder dbConfigurationBuilder = mariaDB4jSpringService.getConfiguration();
        dbConfigurationBuilder.addArg("--character-set-server=utf8");
        dbConfigurationBuilder.addArg("--lower_case_table_names=1");
        dbConfigurationBuilder.addArg("--collation-server=utf8_general_ci");
        dbConfigurationBuilder.addArg("--max-connections=1024");
        dbConfigurationBuilder.addArg("--user=root");
        mariaDB4jSpringService.setDefaultPort(DEFAULT_PORT);
        try {
            mariaDB4jSpringService.start();
            log.info("mariadb4j service started");
        } catch (Exception exception) {
            log.error("mariadb4j service start failed", exception);
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

    }
}
