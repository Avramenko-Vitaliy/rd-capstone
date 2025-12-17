package com.rd.api.controllers;

import com.rd.api.dtos.TodoDto;
import com.rd.api.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Api.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class TodosController {

    private final TodoService service;

    @GetMapping(Api.Todos.TODOS)
    public List<TodoDto> getTodos() {
        return service.getTodos();
    }
}
