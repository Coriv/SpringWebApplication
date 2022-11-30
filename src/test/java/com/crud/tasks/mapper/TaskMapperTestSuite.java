package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskMapperTestSuite {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    void mapToTaskAndBackToTaskDtoTest() {
        //Given
        TaskDto taskDto = new TaskDto("Test task", "Test content");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        TaskDto taskDtoAgain = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(task.getTitle(), "Test task");
        assertEquals(task.getId(), taskDtoAgain.getId());
        assertEquals(taskDtoAgain.getTitle(), "Test task");
        assertEquals(taskDtoAgain.getContent(), "Test content");
    }

    @Test
    void mapToTaskDtoList() {
        //Given
        List<Task> tasks = Arrays.asList(
                new Task("Test task", "Test content"),
                new Task("second task", "some content")
        );
        //When
        List<TaskDto> tasksDto = taskMapper.mapToTaskDtoList(tasks);
        String contentTask1 = tasks.get(0).getContent();
        String resultContent = tasksDto.get(0).getContent();
        String titleTask2 = tasks.get(1).getTitle();
        String resultTitle = tasksDto.get(1).getTitle();

        //Then
        assertEquals(tasks.size(), tasksDto.size());
        assertEquals(contentTask1, resultContent);
        assertEquals(titleTask2, resultTitle);
    }
}