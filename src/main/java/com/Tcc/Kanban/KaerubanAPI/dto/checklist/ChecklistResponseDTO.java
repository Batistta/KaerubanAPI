package com.Tcc.Kanban.KaerubanAPI.dto.checklist;

import com.Tcc.Kanban.KaerubanAPI.dto.checklist.item.ChecklistItemResponseDTO;

import java.util.List;
import java.util.UUID;

public record ChecklistResponseDTO(
        UUID idChecklist,
        String title,
        Integer position,
        UUID cardId,
        List<ChecklistItemResponseDTO> items
) {}
