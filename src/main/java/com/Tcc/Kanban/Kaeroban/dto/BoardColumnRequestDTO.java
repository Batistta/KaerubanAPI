package com.Tcc.Kanban.Kaeroban.dto;

public record BoardColumnRequestDTO(
        String title,
        String color,
        Integer position,
        Integer wipLimit,
        Integer boardId
) {}