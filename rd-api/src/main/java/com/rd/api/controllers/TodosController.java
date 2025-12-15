package com.rd.api.controllers;

import com.rd.api.dtos.Pagination;
import com.rd.api.dtos.TodoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Api.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class TodosController {

    @GetMapping(Api.Todos.TODOS)
    public Pagination<TodoDto> getTodos() {
        return new Pagination<>();
    }
}
