package de.neuefische.todo.backend.security;

public record LoginData(
        String username,
        String password
) {
}
