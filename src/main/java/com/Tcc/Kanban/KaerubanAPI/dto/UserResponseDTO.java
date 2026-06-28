package com.Tcc.Kanban.KaerubanAPI.dto;


import java.time.LocalDateTime;

public record UserResponseDTO(
        Integer idUser,
        String name,
        String email,
        LocalDateTime createdAt
) {}