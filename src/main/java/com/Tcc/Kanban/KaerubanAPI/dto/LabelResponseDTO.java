package com.Tcc.Kanban.KaerubanAPI.dto;

public record LabelResponseDTO(
        Integer idBoard,
        Integer idLabel,
        String name,
        String color
) {
}
