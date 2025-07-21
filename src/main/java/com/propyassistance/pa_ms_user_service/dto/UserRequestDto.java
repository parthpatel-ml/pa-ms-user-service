package com.propyassistance.pa_ms_user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    private String oktaId;

    @NotBlank
    private String username;

    @Email
    private String email;

    private String phoneNumber;

    @NotBlank
    private String role;
}