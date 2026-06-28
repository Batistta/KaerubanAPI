package com.Tcc.Kanban.KaerubanAPI.dto;

public record ChecklistItemRequestDTO(
        String content,
        Boolean isChecked,
        Integer position,
        Integer checklistId
) {}
