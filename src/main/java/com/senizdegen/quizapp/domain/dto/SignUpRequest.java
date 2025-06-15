package com.senizdegen.quizapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Sign up request")
public class SignUpRequest {
    @Schema(description = "Username", example = "Dos")
    @Size(min = 4, max = 20, message = "Username length must be between 4 and 20")
    private String username;

    @Schema(description = "Email", example = "senizdegen@gmail.com")
    @Size(min = 5, max = 255, message = "Email length must be between 5 abd 255")
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Email should format must be: user@example.com")
    private String email;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(max = 255, message = "Password length must be between 8 and 255")
    private String password;
}
