package com.tdila.taskmanger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskPredictionResponse {
    private String priority;
    private Double estimatedTime;
}
