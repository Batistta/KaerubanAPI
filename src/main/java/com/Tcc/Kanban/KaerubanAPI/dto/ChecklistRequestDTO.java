package com.Tcc.Kanban.KaerubanAPI.dto;

public record ChecklistRequestDTO(
        String title,
        Integer position,
        Integer cardId
) {}