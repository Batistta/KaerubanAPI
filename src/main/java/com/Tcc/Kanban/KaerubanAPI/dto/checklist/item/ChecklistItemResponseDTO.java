package com.Tcc.Kanban.KaerubanAPI.dto.checklist.item;

import java.util.UUID;

public record ChecklistItemResponseDTO(
        UUID idItem,
        String content,
        Boolean isChecked,
        Integer position,
        UUID checklistId
) {}
