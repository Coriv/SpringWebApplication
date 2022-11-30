package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.respository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTestSuite {

    @InjectMocks
    private DbService service;

    @Mock
    private TaskRepository repository;

    @Test
    void getAllTask() {
        //Given
        Task task = new Task("Title test", "Content");
        Task task2 = new Task("Second title", "Some content");
        List<Task> tasks = Arrays.asList(task, task2);
        //List<Task> tasks = service.getAllTask();
        when(repository.findAll()).thenReturn(tasks);
        //When
        List<Task> resultList = service.getAllTask();
        String content = tasks.get(1).getContent();
        String resultContent = resultList.get(1).getContent();
        //Then
        assertEquals(resultList.size(), tasks.size());
        assertEquals(content, resultContent);
    }

    @Test
    void getTask() throws TaskNotFoundException {
        //Given
        Task task = new Task("Title test", "Content");
        long id = task.getId();
        when(repository.findById(id)).thenReturn(Optional.of(task));
        //When
        Task resultTask = service.getTask(id);
        //Then
        assertEquals(resultTask.getId(), task.getId());
        assertEquals(resultTask.getTitle(), task.getTitle());
        assertEquals(resultTask.getContent(), task.getContent());
    }

    @Test
    void saveTaskTest() {
        //Given
        Task task = new Task("Title test", "Content");
        when(repository.save(task)).thenReturn(task);
        //When
        Task resultTask = service.saveTask(task);
        //Then
        assertEquals(resultTask.getId(), task.getId());
        assertEquals(resultTask.getTitle(), task.getTitle());
        assertEquals(resultTask.getContent(), task.getContent());
    }

    @Test
    void deleteTask() {
        //Given
        Task task = new Task("Title test", "Content");
        long id = task.getId();
        //When
        service.deleteTask(id);
        //Then
        verify(repository, times(1)).deleteById(id);
    }
}