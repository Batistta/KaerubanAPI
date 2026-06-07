package com.Tcc.Kanban.Kaeroban.dto;

public record ChecklistItemResponseDTO(
        Integer idItem,
        String content,
        Boolean isChecked,
        Integer position,
        Integer checklistId
) {}
