package com.tdila.taskmanger.service;

import com.tdila.taskmanger.dto.TaskCreateRequest;
import com.tdila.taskmanger.model.Task;
import com.tdila.taskmanger.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final WebClient webClient;

    @Transactional
    public Task createTask(TaskCreateRequest request){
        Map<String, Object> response = webClient.post()
                .uri("/predict")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String priority = (String) response.get("priority");
        Double time = ((Number) response.get("estimated_time")).doubleValue();

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .predictedPriority(priority)
                .predictedCompletedTime(time)
                .createdAt(LocalDateTime.now())
                .build();

        return taskRepository.save(task);
    }
}
