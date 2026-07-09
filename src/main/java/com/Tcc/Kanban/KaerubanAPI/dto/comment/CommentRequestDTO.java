package com.Tcc.Kanban.KaerubanAPI.dto.comment;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CommentRequestDTO(
        String content,
        @NotNull UUID userId,
        @NotNull UUID cardId
) {}