package com.rd.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@Table(name = "todos")
public class Todo {

    @Id
    private Integer id;

    private String name;
    private LocalDateTime date;
    private String description;
}
