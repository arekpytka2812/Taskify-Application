package com.pytka.taskifyapplication.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}