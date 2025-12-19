package com.rd.api.services;

import com.rd.api.controllers.params.TodoParams;
import com.rd.api.dtos.Pagination;
import com.rd.api.dtos.TodoDto;
import com.rd.api.entities.Todo;
import com.rd.api.mappers.TodoMapper;
import com.rd.api.repositories.TodosRepository;
import com.rd.api.repositories.specifications.SpecificationBuilder;
import com.rd.api.repositories.specifications.TodosSpecifications;
import com.rd.api.utils.QueryUtils;
import com.rd.api.utils.TypeConvertor;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodosRepository repository;
    private final TodoMapper mapper;

    private static final String DEFAULT_SORT = "name";
    private static final Map<String, String[]> MAPPED_SORT = Map.ofEntries(
            Map.entry("name", new String[]{"name"}),
            Map.entry("status", new String[]{"status.apiKey"}),
            Map.entry("date", new String[]{"date"})
    );

    @Transactional(readOnly = true)
    public Pagination<TodoDto> getTodos(TodoParams params) {
        Specification<Todo> specification = new SpecificationBuilder<Todo>()
                .add(TodosSpecifications.search(params.getSearch()))
                .add(TodosSpecifications.status(params.getStatus()))
                .add(TodosSpecifications.from(params.getFrom()))
                .add(TodosSpecifications.to(params.getTo()))
                .build();
        Pageable pageable = QueryUtils.buildPageRequest(params, MAPPED_SORT, DEFAULT_SORT);
        Page<Todo> page = repository.findAll(specification, pageable);
        return new Pagination<>(page.stream().map(mapper::toDto).toList(), page.getTotalElements());
    }

    @Transactional
    public TodoDto createTodo(TodoDto dto) {
        Todo entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public TodoDto updateTodo(TodoDto dto) {
        Todo todo = findTodoById(dto.getId());
        todo.setName(dto.getName());
        todo.setDate(dto.getDate());
        todo.setStatus(TypeConvertor.toEntity(dto.getStatus()));
        todo.setDescription(dto.getDescription());
        return mapper.toDto(todo);
    }

    @Transactional(readOnly = true)
    public TodoDto getTodo(Integer id) {
        return mapper.toDto(findTodoById(id));
    }

    @Transactional
    public void deleteTodo(Integer id) {
        repository.deleteById(id);
    }

    protected Todo findTodoById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo by ID not found!"));
    }
}
