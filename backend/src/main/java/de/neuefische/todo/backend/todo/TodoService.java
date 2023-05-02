package de.neuefische.todo.backend.todo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
class TodoService {

    private final TodoRepository todoRepository;
    private final CloudinaryService cloudinaryService;

    TodoService(TodoRepository todoRepository, CloudinaryService cloudinaryService) {
        this.todoRepository = todoRepository;
        this.cloudinaryService = cloudinaryService;
    }

    List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public Todo save(Todo todo, MultipartFile image) throws IOException {
        String id = UUID.randomUUID().toString();
        Todo todoToSave = todo.withId(id);

        if (image != null) {
            String url = cloudinaryService.uploadImage(image);
            todoToSave = todoToSave.withUrl(url);
        }

        return todoRepository.save(todoToSave);
    }

    public Todo getById(String id) {
        return todoRepository.findById(id).orElseThrow();
    }

    public Todo update(Todo todo) {
        return todoRepository.save(todo);
    }

    public void delete(String id) {
        todoRepository.deleteById(id);
    }
}





