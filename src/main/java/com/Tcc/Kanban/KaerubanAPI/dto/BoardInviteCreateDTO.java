package com.Tcc.Kanban.KaerubanAPI.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

//para criar o convite
public record BoardInviteCreateDTO(
        @NotNull Integer boardId,
        LocalDate expiresAt
) {
}
