package com.example.project.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpDTO {

    @NotBlank
    private String username;

    @Email @NotBlank
    private String email;

    @NotBlank @Size(min = 6)
    private String password;
}