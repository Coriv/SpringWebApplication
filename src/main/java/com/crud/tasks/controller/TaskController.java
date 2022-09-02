package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @GetMapping
    public TaskDto getTask(Long id) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @DeleteMapping
    public void deleteTask(Long id) {

    }

    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @PostMapping
    public void createTask(TaskDto taskDto) {

    }
}
