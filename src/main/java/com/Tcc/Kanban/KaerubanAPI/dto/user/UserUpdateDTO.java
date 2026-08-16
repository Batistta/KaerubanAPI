package com.Tcc.Kanban.KaerubanAPI.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @Size(min = 2, max = 100) String name,
        @Email String email,
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres") String password
) {}