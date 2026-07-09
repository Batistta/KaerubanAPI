package com.Tcc.Kanban.KaerubanAPI.dto.invite;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BoardInviteRedeemDTO(
        @NotBlank String code,
        @NotNull UUID idUser
) {}
