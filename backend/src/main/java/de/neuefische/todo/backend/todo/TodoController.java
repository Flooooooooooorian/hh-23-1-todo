package de.neuefische.todo.backend.todo;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/todo")
class TodoController {

    private final TodoService todoService;

    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    List<Todo> getAll() {
        return todoService.getAll();
    }

    @PostMapping
    Todo postTodo(@RequestPart("data") @Valid Todo todo, @RequestPart(name = "file", required = false) MultipartFile image) throws IOException {
        return todoService.save(todo, image);
    }

    @GetMapping("{id}")
    Todo getTodoById(@PathVariable String id) {
        return todoService.getById(id);


//      Alternativen mit Try-Catch
//        try {
//            return todoService.getById(id);
//        }
//        catch (NoSuchElementException exception) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found!", exception);
//        }

//        try {
//            return new ResponseEntity<>(todoService.getById(id), HttpStatus.OK);
//        }
//        catch (NoSuchElementException exception) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }

    @PutMapping(path = {"{id}/update", "{id}"})
    Todo update(@PathVariable String id, @RequestBody Todo todo) {
        if (!todo.id().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The id in the url does not match the request body's id");
        }
        return todoService.update(todo);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable String id) {
        todoService.delete(id);
    }
 }






