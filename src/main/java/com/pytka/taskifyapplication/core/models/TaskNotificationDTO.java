package com.pytka.taskifyapplication.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class TaskNotificationDTO {

    @JsonProperty("message")
    private String message;
}
