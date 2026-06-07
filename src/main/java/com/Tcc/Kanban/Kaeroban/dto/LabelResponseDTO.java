package com.Tcc.Kanban.Kaeroban.dto;

public record LabelResponseDTO(
        Integer idBoard,
        Integer idLabel,
        String name,
        String color
) {
}
