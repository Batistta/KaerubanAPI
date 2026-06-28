package com.Tcc.Kanban.KaerubanAPI.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoardInviteRedeemDTO(
        @NotBlank String code,
        @NotNull Integer idUser
) {}
