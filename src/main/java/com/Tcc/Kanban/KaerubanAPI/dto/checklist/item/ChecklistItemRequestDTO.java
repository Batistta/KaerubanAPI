package com.Tcc.Kanban.KaerubanAPI.dto.checklist.item;

import java.util.UUID;

public record ChecklistItemRequestDTO(
        String content,
        Boolean isChecked,
        Integer position,
        UUID checklistId
) {}
