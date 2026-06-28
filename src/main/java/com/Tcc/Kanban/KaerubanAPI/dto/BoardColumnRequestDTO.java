package com.Tcc.Kanban.KaerubanAPI.dto;

public record BoardColumnRequestDTO(
        String title,
        String color,
        Integer position,
        Integer wipLimit,
        Integer boardId
) {}