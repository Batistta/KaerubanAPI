package com.Tcc.Kanban.KaerubanAPI.dto.column;

import java.util.UUID;

public record BoardColumnResponseDTO(
        UUID idColumn,
        String title,
        String color,
        Integer position,
        Integer wipLimit,
        UUID boardId
) {}
