package com.rd.api.controllers;

import com.rd.api.BaseIntegrationTest;
import com.rd.api.configs.DbChecker;
import com.rd.api.dtos.TodoDto;
import com.rd.api.entities.Status;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class TodosControllerTest extends BaseIntegrationTest {

    @Test
    void getTodos() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .get("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("total", equalTo(20))
                .body("data", hasSize(15))
                .body("data[0].id", equalTo(15))
                .body("data[0].name", equalTo("API documentation update"))
                .body("data[0].date", equalTo("2024-01-29T13:45:00"))
                .body("data[0].status", equalTo("ACTIVE"))
                .body("data[0].description", equalTo("Update Swagger/OpenAPI specifications"))
                .body("data[1].id", equalTo(19))
                .body("data[1].name", equalTo("Backup strategy implementation"))
                .body("data[1].date", equalTo("2024-02-02T14:00:00"))
                .body("data[1].status", equalTo("DONE"))
                .body("data[1].description", equalTo("Implement automated backup solution"))
                .body("data[2].id", equalTo(14))
                .body("data[2].name", equalTo("Bug triage"))
                .body("data[2].date", equalTo("2024-01-28T11:00:00"))
                .body("data[2].status", equalTo("DONE"))
                .body("data[2].description", equalTo("Review and prioritize reported bugs"))
                .body("data[3].id", equalTo(13))
                .body("data[3].name", equalTo("Client meeting preparation"))
                .body("data[3].date", equalTo("2024-01-27T08:00:00"))
                .body("data[3].status", equalTo("ACTIVE"))
                .body("data[3].description", equalTo("Prepare demo and presentation materials"))
                .body("data[4].id", equalTo(9))
                .body("data[4].name", equalTo("Code refactoring"))
                .body("data[4].date", equalTo("2024-01-23T10:45:00"))
                .body("data[4].status", equalTo("DONE"))
                .body("data[4].description", equalTo("Refactor legacy code in services layer"))
                .body("data[5].id", equalTo(17))
                .body("data[5].name", equalTo("Code review guidelines"))
                .body("data[5].date", equalTo("2024-01-31T09:30:00"))
                .body("data[5].status", equalTo("DONE"))
                .body("data[5].description", equalTo("Document code review standards and practices"))
                .body("data[6].id", equalTo(1))
                .body("data[6].name", equalTo("Complete project documentation"))
                .body("data[6].date", equalTo("2024-01-15T09:00:00"))
                .body("data[6].status", equalTo("ACTIVE"))
                .body("data[6].description", equalTo("Write comprehensive documentation for the API"))
                .body("data[7].id", equalTo(8))
                .body("data[7].name", equalTo("Database migration"))
                .body("data[7].date", equalTo("2024-01-22T09:15:00"))
                .body("data[7].status", equalTo("CANCELED"))
                .body("data[7].description", equalTo("Create migration scripts for new schema changes"))
                .body("data[8].id", equalTo(4))
                .body("data[8].name", equalTo("Fix bug in authentication"))
                .body("data[8].date", equalTo("2024-01-18T08:45:00"))
                .body("data[8].status", equalTo("CANCELED"))
                .body("data[8].description", equalTo("Resolve authentication token expiration issue"))
                .body("data[9].id", equalTo(5))
                .body("data[9].name", equalTo("Implement new feature"))
                .body("data[9].date", equalTo("2024-01-19T11:20:00"))
                .body("data[9].status", equalTo("ACTIVE"))
                .body("data[9].description", equalTo("Add filtering capability to todos endpoint"))
                .body("data[10].id", equalTo(18))
                .body("data[10].name", equalTo("Integration testing"))
                .body("data[10].date", equalTo("2024-02-01T10:00:00"))
                .body("data[10].status", equalTo("ACTIVE"))
                .body("data[10].description", equalTo("Create end-to-end integration test suite"))
                .body("data[11].id", equalTo(11))
                .body("data[11].name", equalTo("Performance optimization"))
                .body("data[11].date", equalTo("2024-01-25T12:30:00"))
                .body("data[11].status", equalTo("DONE"))
                .body("data[11].description", equalTo("Optimize slow database queries"))
                .body("data[12].id", equalTo(2))
                .body("data[12].name", equalTo("Review pull requests"))
                .body("data[12].date", equalTo("2024-01-16T10:30:00"))
                .body("data[12].status", equalTo("DONE"))
                .body("data[12].description", equalTo("Review and merge pending pull requests"))
                .body("data[13].id", equalTo(10))
                .body("data[13].name", equalTo("Security audit"))
                .body("data[13].date", equalTo("2024-01-24T16:00:00"))
                .body("data[13].status", equalTo("ACTIVE"))
                .body("data[13].description", equalTo("Perform security vulnerability assessment"))
                .body("data[14].id", equalTo(7))
                .body("data[14].name", equalTo("Setup CI/CD pipeline"))
                .body("data[14].date", equalTo("2024-01-21T15:30:00"))
                .body("data[14].status", equalTo("ACTIVE"))
                .body("data[14].description", equalTo("Configure automated deployment workflow"));
    }

    @Test
    public void getTodosPage() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("limit", 2)
                .queryParam("offset", 2)
                .get("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("total", equalTo(20))
                .body("data", hasSize(2))
                .body("data[0].id", equalTo(14))
                .body("data[1].id", equalTo(13));
    }

    @Test
    public void sortTodos() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("sort", "date")
                .queryParam("asc", true)
                .get("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(15))
                .body("data[0].date", equalTo("2024-01-15T09:00:00"))
                .body("data[1].date", equalTo("2024-01-16T10:30:00"))
                .body("data[2].date", equalTo("2024-01-17T14:15:00"))
                .body("data[3].date", equalTo("2024-01-18T08:45:00"))
                .body("data[4].date", equalTo("2024-01-19T11:20:00"))
                .body("data[5].date", equalTo("2024-01-20T13:00:00"))
                .body("data[6].date", equalTo("2024-01-21T15:30:00"))
                .body("data[7].date", equalTo("2024-01-22T09:15:00"))
                .body("data[8].date", equalTo("2024-01-23T10:45:00"))
                .body("data[9].date", equalTo("2024-01-24T16:00:00"))
                .body("data[10].date", equalTo("2024-01-25T12:30:00"))
                .body("data[11].date", equalTo("2024-01-26T14:20:00"))
                .body("data[12].date", equalTo("2024-01-27T08:00:00"))
                .body("data[13].date", equalTo("2024-01-28T11:00:00"))
                .body("data[14].date", equalTo("2024-01-29T13:45:00"));

        given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("sort", "date")
                .queryParam("asc", false)
                .get("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(15))
                .body("data[0].date", equalTo("2024-02-03T16:30:00"))
                .body("data[1].date", equalTo("2024-02-02T14:00:00"))
                .body("data[2].date", equalTo("2024-02-01T10:00:00"))
                .body("data[3].date", equalTo("2024-01-31T09:30:00"))
                .body("data[4].date", equalTo("2024-01-30T15:15:00"))
                .body("data[5].date", equalTo("2024-01-29T13:45:00"))
                .body("data[6].date", equalTo("2024-01-28T11:00:00"))
                .body("data[7].date", equalTo("2024-01-27T08:00:00"))
                .body("data[8].date", equalTo("2024-01-26T14:20:00"))
                .body("data[9].date", equalTo("2024-01-25T12:30:00"))
                .body("data[10].date", equalTo("2024-01-24T16:00:00"))
                .body("data[11].date", equalTo("2024-01-23T10:45:00"))
                .body("data[12].date", equalTo("2024-01-22T09:15:00"))
                .body("data[13].date", equalTo("2024-01-21T15:30:00"))
                .body("data[14].date", equalTo("2024-01-20T13:00:00"));

        given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("sort", "status")
                .queryParam("asc", true)
                .get("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(15))
                .body("data[0].status", equalTo("ACTIVE"))
                .body("data[1].status", equalTo("ACTIVE"))
                .body("data[2].status", equalTo("ACTIVE"))
                .body("data[3].status", equalTo("ACTIVE"))
                .body("data[4].status", equalTo("ACTIVE"))
                .body("data[5].status", equalTo("ACTIVE"))
                .body("data[6].status", equalTo("ACTIVE"))
                .body("data[7].status", equalTo("ACTIVE"))
                .body("data[8].status", equalTo("CANCELED"))
                .body("data[9].status", equalTo("CANCELED"))
                .body("data[10].status", equalTo("CANCELED"))
                .body("data[11].status", equalTo("CANCELED"))
                .body("data[12].status", equalTo("CANCELED"))
                .body("data[13].status", equalTo("DONE"))
                .body("data[14].status", equalTo("DONE"));

        given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("sort", "status")
                .queryParam("asc", false)
                .get("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(15))
                .body("data[0].status", equalTo("DONE"))
                .body("data[1].status", equalTo("DONE"))
                .body("data[2].status", equalTo("DONE"))
                .body("data[3].status", equalTo("DONE"))
                .body("data[4].status", equalTo("DONE"))
                .body("data[5].status", equalTo("DONE"))
                .body("data[6].status", equalTo("DONE"))
                .body("data[7].status", equalTo("CANCELED"))
                .body("data[8].status", equalTo("CANCELED"))
                .body("data[9].status", equalTo("CANCELED"))
                .body("data[10].status", equalTo("CANCELED"))
                .body("data[11].status", equalTo("CANCELED"))
                .body("data[12].status", equalTo("ACTIVE"))
                .body("data[13].status", equalTo("ACTIVE"))
                .body("data[14].status", equalTo("ACTIVE"));
    }

    @Test
    public void filterTodos() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("status", "DONE")
                .get("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("total", equalTo(7))
                .body("data", hasSize(7))
                .body("data.status", everyItem(equalTo("DONE")));

        given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("from", "2024-02-01T00:00:00")
                .get("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("total", equalTo(3))
                .body("data[0].id", equalTo(19))
                .body("data[1].id", equalTo(18))
                .body("data[2].id", equalTo(20));

        given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("search", "documentation")
                .get("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("total", equalTo(2))
                .body("data[0].name", containsStringIgnoringCase("documentation"))
                .body("data[1].name", containsStringIgnoringCase("documentation"));
    }

    @Test
    public void validateCreateTodo() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(new TodoDto()))
                .post("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("errors", hasSize(3))
                .body("errors.field", containsInAnyOrder("name", "date", "status"));

        TodoDto dto = TodoDto.builder()
                .name("a".repeat(81))
                .date(LocalDateTime.parse("2024-05-01T10:00:00"))
                .status(Status.Type.ACTIVE)
                .build();
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(dto))
                .post("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("errors[0].field", equalTo("name"))
                .body("errors[0].message", containsString("size must be between 0 and 80"));

        dto.setName("name");
        dto.setDescription("a".repeat(256));
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(dto))
                .post("/rd-api/todos")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("errors[0].field", equalTo("description"))
                .body("errors[0].message", containsString("size must be between 0 and 255"));
    }

    @Test
    public void createTodo() {
        dbChecker.checkDb(new DbChecker.ExpectedData().addRow("20"), "SELECT count(*) FROM todos");

        TodoDto todo = TodoDto.builder()
                .name("New Todo")
                .date(LocalDateTime.parse("2024-05-01T10:00:00"))
                .status(Status.Type.ACTIVE)
                .description("New Description")
                .build();

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(todo))
                .post("/rd-api/todos")
                .then()
                .statusCode(201)
                .body("id", equalTo(1000))
                .body("name", equalTo("New Todo"))
                .body("date", equalTo("2024-05-01T10:00:00"))
                .body("status", equalTo("ACTIVE"))
                .body("description", equalTo("New Description"));

        dbChecker.checkDb(new DbChecker.ExpectedData().addRow("21"), "SELECT count(*) FROM todos");
        dbChecker.checkDb(
                new DbChecker.ExpectedData().addRow("1000", "New Todo", "ACTIVE", "2024-05-01 10:00:00", "New Description"),
                "SELECT t.id, t.name, s.api_key, t.date, t.description FROM todos t JOIN statuses s ON t.status_id = s.id WHERE t.id = 1000"
        );
    }

    @Test
    public void validateUpdateTodo() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(new TodoDto()))
                .put("/rd-api/todos/1")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("errors", hasSize(3))
                .body("errors.field", containsInAnyOrder("name", "date", "status"));

        TodoDto dto = TodoDto.builder()
                .name("a".repeat(81))
                .date(LocalDateTime.parse("2024-05-01T10:00:00"))
                .status(Status.Type.ACTIVE)
                .build();
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(dto))
                .put("/rd-api/todos/1")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("errors[0].field", equalTo("name"))
                .body("errors[0].message", containsString("size must be between 0 and 80"));

        dto.setName("name");
        dto.setDescription("a".repeat(256));
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(dto))
                .put("/rd-api/todos/1")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("errors[0].field", equalTo("description"))
                .body("errors[0].message", containsString("size must be between 0 and 255"));
    }

    @Test
    public void updateTodo() {
        dbChecker.checkDb(new DbChecker.ExpectedData().addRow("20"), "SELECT count(*) FROM todos");
        dbChecker.checkDb(
                new DbChecker.ExpectedData().addRow("1", "Complete project documentation", "ACTIVE", "2024-01-15 09:00:00", "Write comprehensive documentation for the API"),
                "SELECT t.id, name, s.api_key, date, description FROM todos t JOIN Statuses s ON t.status_id = s.id WHERE t.id = 1"
        );

        String name = "Updated Todo";
        LocalDateTime date = LocalDateTime.parse("2024-06-01T10:00:00");
        Status.Type status = Status.Type.DONE;
        String description = "Updated Description";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(TodoDto.builder()
                        .name(name)
                        .date(date)
                        .status(status)
                        .description(description)
                        .build()))
                .put("/rd-api/todos/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(1))
                .body("name", equalTo(name))
                .body("date", equalTo("2024-06-01T10:00:00"))
                .body("status", equalTo(status.name()))
                .body("description", equalTo(description));

        dbChecker.checkDb(new DbChecker.ExpectedData().addRow("20"), "SELECT count(*) FROM todos");
        dbChecker.checkDb(
                new DbChecker.ExpectedData().addRow("1", name, status.name(), "2024-06-01 10:00:00", description),
                "SELECT t.id, name, s.api_key, date, description FROM todos t JOIN Statuses s ON t.status_id = s.id WHERE t.id = 1"
        );
    }

    @Test
    public void deleteTodo() {
        dbChecker.checkDb(new DbChecker.ExpectedData().addRow("20"), "SELECT count(*) FROM todos");

        given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/rd-api/todos/1")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        dbChecker.checkDb(new DbChecker.ExpectedData().addRow("19"), "SELECT count(*) FROM todos");
        dbChecker.checkDb(new DbChecker.ExpectedData().addRow("0"), "SELECT count(*) FROM todos WHERE id = 1");

        given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/rd-api/todos/" + Integer.MAX_VALUE)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        dbChecker.checkDb(new DbChecker.ExpectedData().addRow("19"), "SELECT count(*) FROM todos");
    }
}
