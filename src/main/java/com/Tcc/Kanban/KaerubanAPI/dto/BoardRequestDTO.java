package com.Tcc.Kanban.KaerubanAPI.dto;

public record BoardRequestDTO(
        String title,
        String color,
        Integer ownerId
) {}
