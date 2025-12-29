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

        String priority = "MEDIUM";
        Double time = 2.0;

        try {
            TaskPredictionResponse prediction = webClient.post()
                    .uri("/predict")
                    .bodyValue(predictionRequest)
                    .retrieve()
                    .bodyToMono(TaskPredictionResponse.class)
                    .block();

            if (prediction != null) {
                priority = prediction.getPriority();
                time = prediction.getEstimatedTime();
            }
        }catch (Exception e){
            System.err.println("ML service unavailable, using default prediction.");
        }

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
