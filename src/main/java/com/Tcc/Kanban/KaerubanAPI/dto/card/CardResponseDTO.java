package com.Tcc.Kanban.KaerubanAPI.dto.card;

import com.Tcc.Kanban.KaerubanAPI.dto.label.LabelResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CardResponseDTO(
        UUID idCard,
        String title,
        String description,
        Integer position,
        String priority,
        LocalDate dueDate,
        LocalDateTime createdAt,
        UUID columnId,
        List<LabelResponseDTO> labels
) {}