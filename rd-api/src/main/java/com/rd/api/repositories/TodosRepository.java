package com.rd.api.repositories;

import com.rd.api.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TodosRepository extends JpaRepository<Todo, Integer>, JpaSpecificationExecutor<Todo> {
}
