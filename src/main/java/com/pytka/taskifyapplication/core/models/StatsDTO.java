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
public class StatsDTO {

    @JsonProperty("workspacesCreated")
    private Long workspacesCreated;

    @JsonProperty("workspacesDeleted")
    private Long workspacesDeleted;

    @JsonProperty("tasksCreated")
    private Long tasksCreated;

    @JsonProperty("tasksDeleted")
    private Long tasksDeleted;

    @JsonProperty("updateInfosCreated")
    private Long updateInfosCreated;

    @JsonProperty("finishedOnTimeTasks")
    private Long finishedOnTimeTasks;

    @JsonProperty("finishedWithDelayTasks")
    private Long finishedWithDelayTasks;

}
