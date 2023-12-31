package com.pytka.taskifyapplication.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("id")
    private Long ID;

    @JsonProperty("username")
    private String username;
}
