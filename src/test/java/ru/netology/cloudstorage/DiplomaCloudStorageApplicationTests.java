package ru.netology.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {DiplomaCloudStorageApplicationTests.Initializer.class})
public class DiplomaCloudStorageApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    private static final int DB_PORT = 5432;
    private static final String DB_NAME = "postgres";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private final static Network NETWORK = Network.newNetwork();

    @Container
    public static PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres")
            .withNetwork(NETWORK)
            .withExposedPorts(DB_PORT)
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD);

    @Test
    void contLoadsPostgres() {
        var portDatabase = dbContainer.getMappedPort(DB_PORT);
        System.out.println(dbContainer.getJdbcUrl() + " " + dbContainer.getDatabaseName() + " " + dbContainer.getPassword());
        System.out.println("Network id: " + NETWORK.getId());
        System.out.println("database -> port: " + portDatabase);
        Assertions.assertTrue(dbContainer.isRunning());
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + dbContainer.getJdbcUrl(),
                    "spring.datasource.username=" + dbContainer.getUsername(),
                    "spring.datasource.password=" + dbContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}