package com.Tcc.Kanban.KaerubanAPI.dto;

public record ChecklistItemResponseDTO(
        Integer idItem,
        String content,
        Boolean isChecked,
        Integer position,
        Integer checklistId
) {}
