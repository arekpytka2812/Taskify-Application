package com.pytka.taskifyapplication.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UserSettingsDTO {

    @JsonProperty("taskTypes")
    private List<String> taskTypes;

    @JsonProperty("taskPriorities")
    private List<String> taskPriorities;
}
