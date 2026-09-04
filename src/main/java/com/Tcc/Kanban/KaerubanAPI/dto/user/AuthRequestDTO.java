package com.Tcc.Kanban.KaerubanAPI.dto.user;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank String email,
        @NotBlank  String password
) {}
