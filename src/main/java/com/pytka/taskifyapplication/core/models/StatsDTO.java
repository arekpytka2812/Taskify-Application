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

    @JsonProperty("tasksCreated")
    private Long tasksCreated;

    @JsonProperty("updateInfosCreated")
    private Long updateInfosCreated;

    @JsonProperty("tasksDeleted")
    private Long tasksDeleted;

    @JsonProperty("finishedOnTimeTasks")
    private Long finishedOnTimeTasks;

    @JsonProperty("finishedWithDelayTasks")
    private Long finishedWithDelayTasks;

}
