package com.Tcc.Kanban.Kaeroban.dto;

import java.util.List;

public record ChecklistResponseDTO(
        Integer idChecklists,
        String title,
        Integer position,
        Integer cardId,
        List<ChecklistItemResponseDTO> items
) {}
