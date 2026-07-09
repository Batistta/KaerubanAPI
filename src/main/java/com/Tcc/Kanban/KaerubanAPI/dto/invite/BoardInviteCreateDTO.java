package com.Tcc.Kanban.KaerubanAPI.dto.invite;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

//para criar o convite
public record BoardInviteCreateDTO(
        @NotNull UUID boardId,
        LocalDate expiresAt,
        String roleTitle
) {
}
