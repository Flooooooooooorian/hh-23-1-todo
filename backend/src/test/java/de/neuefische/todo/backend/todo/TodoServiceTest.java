package de.neuefische.todo.backend.todo;

import com.cloudinary.Uploader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TodoServiceTest {

    TodoRepository todoRepository = mock(TodoRepository.class);
    CloudinaryService cloudinaryService = mock(CloudinaryService.class);

    TodoService todoService = new TodoService(todoRepository, cloudinaryService);

    @Test
    void getAllCallsRepository() {
        // given
        Todo testItem = new Todo("bla", TodoStatus.OPEN);
        Mockito.when(todoRepository.findAll())
                .thenReturn(Collections.singletonList(testItem));

        // when
        List<Todo> actual = todoService.getAll();

        // then
        Assertions.assertThat(actual)
                .containsExactly(testItem);
    }

    @Test
    void postTodo() throws IOException {
        //GIVEN
        Todo todoToSave = new Todo(null, "description", TodoStatus.OPEN, null);
        Todo todoToSaveWithId = new Todo("123", "description", TodoStatus.OPEN, null);


        when(cloudinaryService.uploadImage(any())).thenReturn("url");
        when(todoRepository.save(todoToSaveWithId)).thenReturn(todoToSaveWithId);
        when(uuidService.randomId()).thenReturn("123");

        MultipartFile file = null;

        //WHEN
        Todo actual = todoService.save(todoToSave, file);

        //THEN
        Todo expected = new Todo(null, "description", TodoStatus.OPEN, null);
        assertEquals(expected, actual);
    }
}






