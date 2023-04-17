package de.neuefische.todo.backend.exception;

import java.time.Instant;

public record ApiError(
        String message,
        Instant timestamp
) {
}
