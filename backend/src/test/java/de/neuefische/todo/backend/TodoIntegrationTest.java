package de.neuefische.todo.backend;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.todo.backend.todo.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    Cloudinary cloudinary;

    Uploader uploader = mock(Uploader.class);

    @Test
    @WithMockUser
    void expectEmptyListOnGet() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        []
                        """));
    }

    @Test
    void expect401_OnGet_whenAnonymousUser() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/todo"))
                .andExpect(status().isUnauthorized());
    }

    @DirtiesContext
    @Test
    @WithMockUser
    void expectSuccessfulPost() throws Exception {
        MockMultipartFile data = new MockMultipartFile("data",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                """
                                {
                                "description":"Nächsten Endpunkt implementieren",
                                "status":"OPEN"
                                }
                        """.getBytes());

        MockMultipartFile file = new MockMultipartFile("file",
                "testBild.png",
                MediaType.IMAGE_PNG_VALUE,
                "testBild".getBytes()
        );

        File fileToUpload = File.createTempFile("image", null);
        file.transferTo(fileToUpload);


        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), any())).thenReturn(Map.of("url", "test-url"));


        String actual = mockMvc.perform(
                        multipart("http://localhost:8080/api/todo")
                                .file(data)
                                .file(file)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "description": "Nächsten Endpunkt implementieren",
                          "status": "OPEN",
                          "url": "test-url"
                        }
                        """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Todo actualTodo = objectMapper.readValue(actual, Todo.class);
        assertThat(actualTodo.id())
                .isNotBlank();
    }

    @DirtiesContext
    @Test
    @WithMockUser
    void expectSuccessfulPut() throws Exception {
        String saveResult = mockMvc.perform(
                        post("http://localhost:8080/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {"description":"Nächsten Endpunkt implementieren","status":"OPEN"}
                                        """)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "description": "Nächsten Endpunkt implementieren",
                          "status": "OPEN"
                        }
                        """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Todo saveResultTodo = objectMapper.readValue(saveResult, Todo.class);
        String id = saveResultTodo.id();

        mockMvc.perform(
                        put("http://localhost:8080/api/todo/" + id + "/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {"id":"<ID>","description":"Bla","status":"IN_PROGRESS"}
                                        """.replaceFirst("<ID>", id))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "id": "<ID>",
                          "description": "Bla",
                          "status": "IN_PROGRESS"
                        }
                        """.replaceFirst("<ID>", id)));

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void expectSuccessfulDelete() throws Exception {
        String saveResult = mockMvc.perform(
                        post("http://localhost:8080/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {"description":"Nächsten Endpunkt implementieren","status":"OPEN"}
                                        """)
                                .with(csrf())
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        Todo saveResultTodo = objectMapper.readValue(saveResult, Todo.class);
        String id = saveResultTodo.id();

        mockMvc.perform(delete("http://localhost:8080/api/todo/" + id)
                        .with(csrf()))
                .andExpect(status().isOk());

        mockMvc.perform(get("http://localhost:8080/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        []
                        """));
    }

    @DirtiesContext
    @Test
    @WithMockUser
    void expectTodoOnGetById() throws Exception {
        String actual = mockMvc.perform(
                        post("http://localhost:8080/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {"description":"Nächsten Endpunkt implementieren","status":"OPEN"}
                                        """)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "description": "Nächsten Endpunkt implementieren",
                          "status": "OPEN"
                        }
                        """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Todo actualTodo = objectMapper.readValue(actual, Todo.class);
        String id = actualTodo.id();

        mockMvc.perform(get("http://localhost:8080/api/todo/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "id": "<ID>",
                          "description": "Nächsten Endpunkt implementieren",
                          "status": "OPEN"
                        }
                        """.replaceFirst("<ID>", id)));
    }
}











