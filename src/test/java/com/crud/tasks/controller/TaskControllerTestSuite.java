package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(TaskController.class)
class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    void shouldFetchTasksDtoList() throws Exception {
        //Given
        List<TaskDto> tasksList = List.of(new TaskDto(12L, "Test title", "Content"));
        when(taskController.getTasks()).thenReturn(ResponseEntity.ok(tasksList));

        //When&Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Content")));
    }

    @Test
    void shouldFetchTaskDtoById() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(12L, "Test title", "Content");
        when(taskController.getTask(anyLong())).thenReturn(ResponseEntity.ok(taskDto));

        //When&Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tasks/" + 12L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        when(taskController.deleteTask(anyLong())).thenReturn(ResponseEntity.ok().build());

        //When&Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/tasks/" + 12L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateTaskDetails() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(12L, "Test title", "Content");
        when(taskController.updateTask(any(TaskDto.class))).thenReturn(ResponseEntity.ok(taskDto));

        Gson gson = new Gson();
        String jsonTask = gson.toJson(taskDto);

        //When&Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonTask))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content")));
    }

    @Test
    void shouldAddNewTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(12L, "Test title", "Content");
        when(taskController.createTask(any(TaskDto.class))).thenReturn(ResponseEntity.ok().build());
        Gson gson = new Gson();
        String taskJson = gson.toJson(taskDto);

        //When&Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(taskJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}