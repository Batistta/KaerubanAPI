package com.Tcc.Kanban.KaerubanAPI.dto.card;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record CardSaveDTO(
        @NotNull @Size(min = 2) String title,
        String description,
        Integer position,
        String priority,
        LocalDate dueDate,
        @NotNull UUID columnId
) {}