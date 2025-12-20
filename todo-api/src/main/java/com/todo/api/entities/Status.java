package com.todo.api.entities;

import com.todo.api.utils.ApiKey;
import com.todo.api.utils.TypeCreator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@Entity
@ToString
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "statuses")
public class Status implements ApiKey<Status.Type> {

    @Getter
    @AllArgsConstructor
    public enum Type implements TypeCreator<Status> {
        ACTIVE(1),
        DONE(2),
        CANCELED(3);

        private final int id;

        @Override
        public Status getInstance() {
            return new Status(id, this);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "api_key", unique = true, nullable = false)
    private Type apiKey;
}
