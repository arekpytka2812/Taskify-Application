package com.pytka.taskifyapplication.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class TaskDTO extends AbstractDTO{

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("taskType")
    private String taskType;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("emailNotifications")
    private Boolean emailNotifications;

    @JsonProperty("appNotifications")
    private Boolean appNotifications;

    @JsonProperty("workspaceID")
    private Long workspaceID;

    @JsonProperty("taskUpdates")
    private List<UpdateInfoDTO> taskUpdates;

    @JsonProperty("expirationDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime expirationDate;
}