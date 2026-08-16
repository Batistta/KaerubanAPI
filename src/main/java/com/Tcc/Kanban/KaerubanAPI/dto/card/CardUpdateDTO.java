package com.Tcc.Kanban.KaerubanAPI.dto.card;

import java.time.LocalDate;
import java.util.UUID;

public record CardUpdateDTO(
        String title,
        String description,
        Integer position,
        String priority,
        LocalDate dueDate,
        UUID columnId
) {
}
