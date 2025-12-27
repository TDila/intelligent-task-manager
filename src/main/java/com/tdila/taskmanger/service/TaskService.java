package com.tdila.taskmanger.service;

import com.tdila.taskmanger.dto.TaskCreateRequest;
import com.tdila.taskmanger.model.Task;
import com.tdila.taskmanger.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    public Task createTask(TaskCreateRequest request){
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .predictedPriority("MEDIUM")
                .predictedCompletedTime(4.0)
                .createdAt(LocalDateTime.now())
                .build();

        return taskRepository.save(task);
    }
}
