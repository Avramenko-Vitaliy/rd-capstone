package com.rd.api.controllers;

import com.rd.api.BaseIntegrationTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SqlGroup(@Sql("classpath:test-clean.sql"))
public class VersionControllerTest extends BaseIntegrationTest {

    @Test
    public void testVersion() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/rd-api/version")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo("latest"));
    }
}
