package com.tdila.taskmanger.service;

import com.tdila.taskmanger.dto.TaskCreateRequest;
import com.tdila.taskmanger.dto.TaskPredictionRequest;
import com.tdila.taskmanger.dto.TaskPredictionResponse;
import com.tdila.taskmanger.model.Task;
import com.tdila.taskmanger.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final WebClient webClient;

    @Transactional
    public Task createTask(TaskCreateRequest request){
        TaskPredictionRequest predictionRequest =
                new TaskPredictionRequest(request.getTitle(), request.getDescription());

        TaskPredictionResponse prediction = webClient.post()
                .uri("/predict")
                .bodyValue(predictionRequest)
                .retrieve()
                .bodyToMono(TaskPredictionResponse.class)
                .block();

        String priority = prediction.getPriority();
        Double time = prediction.getEstimatedTime();

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
