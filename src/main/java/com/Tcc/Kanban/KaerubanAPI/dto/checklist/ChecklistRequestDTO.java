package com.Tcc.Kanban.KaerubanAPI.dto.checklist;

import java.util.UUID;

public record ChecklistRequestDTO(
        String title,
        Integer position,
        UUID cardId
) {}