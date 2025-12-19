package com.rd.api.controllers;

import com.rd.api.controllers.params.TodoParams;
import com.rd.api.dtos.Pagination;
import com.rd.api.dtos.TodoDto;
import com.rd.api.exception.APIError;
import com.rd.api.services.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Api.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class TodosController {

    private final TodoService service;

    @GetMapping(Api.Todos.TODOS)
    @Operation(
            description = "Get a page of todos",
            parameters = {
                    @Parameter(
                            name = "sort",
                            in = ParameterIn.QUERY,
                            description = "If sorting column unknown will be used default (payment_id).<br/>" +
                                    "If direction unknown will be used ASC direction.<br/><br/>" +
                                    "Allowed sorting by:<br/>" +
                                    "name<br/>" +
                                    "date<br/>" +
                                    "status<br/>"
                    )
            }
    )
    public Pagination<TodoDto> getTodos(@ModelAttribute TodoParams params) {
        return service.getTodos(params);
    }

    @GetMapping(Api.Todos.TODO_BY_ID)
    @Operation(description = "Get a todo by id", responses = {
            @ApiResponse(responseCode = "200", description = "Successful", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Todo not found", content = @Content(schema = @Schema(implementation = APIError.class)))
    })
    public TodoDto getTodo(@PathVariable Integer id) {
        return service.getTodo(id);
    }

    @PostMapping(Api.Todos.TODOS)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create a ToDo", responses = {
            @ApiResponse(responseCode = "201", description = "Successful created", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "ToDo not found", content = @Content(schema = @Schema(implementation = APIError.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = APIError.class)))
    })
    public TodoDto createTodo(@Validated @RequestBody TodoDto dto) {
        dto.setId(null);
        return service.createTodo(dto);
    }

    @PutMapping(Api.Todos.TODO_BY_ID)
    @Operation(description = "Update the ToDo by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successful updated", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "ToDo not found", content = @Content(schema = @Schema(implementation = APIError.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = APIError.class)))
    })
    public TodoDto updateTodo(@PathVariable Integer id, @Validated @RequestBody TodoDto dto) {
        dto.setId(id);
        return service.updateTodo(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(Api.Todos.TODO_BY_ID)
    @Operation(description = "Delete the ToDo by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Successful updated", useReturnTypeSchema = true)
    })
    public void deleteTodo(@PathVariable Integer id) {
        service.deleteTodo(id);
    }
}
