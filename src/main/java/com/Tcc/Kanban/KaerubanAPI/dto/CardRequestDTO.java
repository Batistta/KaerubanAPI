package com.Tcc.Kanban.KaerubanAPI.dto;

import java.time.LocalDate;

public record CardRequestDTO(
        String title,
        String description,
        Integer position,
        String priority,
        LocalDate dueDate,
        Integer columnId
) {}