package com.pytka.taskifyapplication.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractDTO {

    @JsonProperty("id")
    private Long ID;

    @JsonProperty("createDate")
    private LocalDateTime createDate;
}
