package com.rd.api.repositories;

import com.rd.api.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodosRepository extends JpaRepository<Todo, Integer> {
}
