package com.Tcc.Kanban.Kaeroban.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CardResponseDTO(
        Integer idCard,
        String title,
        String description,
        Integer position,
        String priority,
        LocalDate dueDate,
        LocalDateTime createdAt,
        Integer columnId,
        List<LabelResponseDTO> labels
) {}