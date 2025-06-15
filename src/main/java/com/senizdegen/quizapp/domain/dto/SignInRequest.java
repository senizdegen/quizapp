package com.senizdegen.quizapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Sign in request")
public class SignInRequest {

    @Schema(description = "Username", example = "Jon")
    @Size(min = 4, max = 20, message = "Username length must be between 4 and 20")
    @NotBlank(message = "Username can not be empty")
    private String username;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(max = 255, message = "Password length must be between 8 and 255")
    @NotBlank(message = "Password can not be empty")
    private String password;
}
