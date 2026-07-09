package com.Tcc.Kanban.KaerubanAPI.dto.label;

import java.util.UUID;

public record LabelRequestDTO(
        UUID idBoard,
        String name,
        String color
) {
}
