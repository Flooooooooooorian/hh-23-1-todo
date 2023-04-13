package de.neuefische.todo.backend.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Todo(
        String id,
        @NotBlank
        @Size(min = 3, max = 32)
        String description,
        TodoStatus status
) {

    Todo(
            String description,
            TodoStatus status
    ) {
        this(null, description, status);
    }


    public Todo withId(String id) {
        return new Todo(id, description, status);
    }
}
