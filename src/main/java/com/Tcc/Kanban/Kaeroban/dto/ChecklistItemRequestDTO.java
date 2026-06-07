package com.Tcc.Kanban.Kaeroban.dto;

public record ChecklistItemRequestDTO(
        String content,
        Boolean isChecked,
        Integer position,
        Integer checklistId
) {}
