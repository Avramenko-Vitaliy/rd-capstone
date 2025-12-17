package com.rd.api.services;

import com.rd.api.dtos.TodoDto;
import com.rd.api.mappers.TodoMapper;
import com.rd.api.repositories.TodosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodosRepository repository;
    private final TodoMapper mapper;

    public List<TodoDto> getTodos() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
