package com.pytka.taskifyapplication.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerificationCodeRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

}
