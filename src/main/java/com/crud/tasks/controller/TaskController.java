package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService dbService;
    private final TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<TaskDto> getTasks() {
        List<Task> tasks = dbService.getAllTask();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(dbService.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @DeleteMapping
    public void deleteTask(Long id) {

    }

    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {
    Task task = taskMapper.mapToTask(taskDto);
    dbService.saveTask(task);
    }
}
