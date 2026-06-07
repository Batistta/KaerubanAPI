package com.Tcc.Kanban.Kaeroban.dto;

public record ChecklistRequestDTO(
        String title,
        Integer position,
        Integer cardId
) {}