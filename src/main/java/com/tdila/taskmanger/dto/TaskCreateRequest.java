package com.tdila.taskmanger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateRequest {
    @NotBlank
    private String title;

    private String description;
}
