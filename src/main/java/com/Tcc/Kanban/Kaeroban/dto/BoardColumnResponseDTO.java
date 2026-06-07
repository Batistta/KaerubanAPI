package com.Tcc.Kanban.Kaeroban.dto;

public record BoardColumnResponseDTO(
        Integer idColumn,
        String title,
        String color,
        Integer position,
        Integer wipLimit,
        Integer boardId
) {}
