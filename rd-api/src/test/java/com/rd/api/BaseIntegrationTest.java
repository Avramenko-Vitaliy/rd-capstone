package com.rd.api;

import com.rd.api.configs.DbChecker;
import com.rd.api.configs.TestConfigs;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tools.jackson.databind.ObjectMapper;

import java.util.Locale;
import java.util.TimeZone;

@ExtendWith(SpringExtension.class)
@SqlGroup({@Sql("classpath:test-clean.sql"), @Sql})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RdApiApplication.class, TestConfigs.class})
public abstract class BaseIntegrationTest {

    @Autowired
    protected DbChecker dbChecker;
    @Autowired
    protected ObjectMapper mapper;

    @Value("${local.server.port}")
    private Integer port;

    static {
        Locale.setDefault(Locale.US);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }
}
