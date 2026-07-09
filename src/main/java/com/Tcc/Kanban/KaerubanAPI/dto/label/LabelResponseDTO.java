package com.Tcc.Kanban.KaerubanAPI.dto.label;

import java.util.UUID;

public record LabelResponseDTO(
        UUID idBoard,
        UUID idLabel,
        String name,
        String color
) {
}
