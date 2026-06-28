package com.Tcc.Kanban.KaerubanAPI.dto;

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