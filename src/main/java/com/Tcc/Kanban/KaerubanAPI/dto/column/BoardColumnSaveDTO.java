package com.Tcc.Kanban.KaerubanAPI.dto.column;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record BoardColumnSaveDTO(
        @NotNull @Size(min = 3, max = 80) String title,
        String color,
        Integer position,
        Integer wipLimit,
        UUID boardId
) {}