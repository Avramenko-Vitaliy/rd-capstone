package com.todo.api.repositories.specifications;

import com.todo.api.entities.Status;
import com.todo.api.entities.Todo;
import com.todo.api.utils.QueryUtils;
import jakarta.persistence.criteria.JoinType;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Objects;

@UtilityClass
public class TodosSpecifications {

    public Specification<Todo> search(String search) {
        if (StringUtils.isBlank(search)) {
            return null;
        }
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("name")), QueryUtils.formatLike(search)),
                cb.like(cb.lower(root.get("description")), QueryUtils.formatLike(search))
        );
    }

    public Specification<Todo> status(Status.Type status) {
        if (Objects.isNull(status)) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.join("status", JoinType.INNER).get("id"), status.getId());
    }

    public Specification<Todo> from(LocalDateTime from) {
        if (Objects.isNull(from)) {
            return null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), from);
    }

    public Specification<Todo> to(LocalDateTime to) {
        if (Objects.isNull(to)) {
            return null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), to);
    }

}
