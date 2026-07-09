package com.Tcc.Kanban.KaerubanAPI.dto.column;

public record BoardColumnUpdateDTO(
        String title,
        String color,
        Integer position,
        Integer wipLimit
) {
}
