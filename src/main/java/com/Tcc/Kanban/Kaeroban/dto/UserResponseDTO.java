package com.Tcc.Kanban.Kaeroban.dto;


import java.time.LocalDateTime;

public record UserResponseDTO(
        Integer idUser,
        String name,
        String email,
        LocalDateTime createdAt
) {}