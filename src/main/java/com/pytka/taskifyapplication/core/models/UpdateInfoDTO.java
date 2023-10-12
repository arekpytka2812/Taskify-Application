package com.pytka.taskifyapplication.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UpdateInfoDTO extends AbstractDTO{

    @JsonProperty("updateInfoDate")
    private LocalDateTime updateInfoDate;

    @JsonProperty("description")
    private String description;
}