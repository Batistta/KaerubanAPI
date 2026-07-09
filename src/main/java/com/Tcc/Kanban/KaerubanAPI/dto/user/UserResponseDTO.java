package com.Tcc.Kanban.KaerubanAPI.dto.user;


import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID idUser,
        String name,
        String email,
        LocalDateTime createdAt
) {}