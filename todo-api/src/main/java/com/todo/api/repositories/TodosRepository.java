package com.todo.api.repositories;

import com.todo.api.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TodosRepository extends JpaRepository<Todo, Integer>, JpaSpecificationExecutor<Todo> {
}
