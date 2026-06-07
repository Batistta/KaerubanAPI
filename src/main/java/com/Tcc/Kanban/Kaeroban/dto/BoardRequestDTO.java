package com.Tcc.Kanban.Kaeroban.dto;

public record BoardRequestDTO(
        String title,
        String color,
        Integer ownerId
) {}
