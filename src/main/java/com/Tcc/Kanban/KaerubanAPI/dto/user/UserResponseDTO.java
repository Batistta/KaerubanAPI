package com.Tcc.Kanban.KaerubanAPI.dto.user;


import com.Tcc.Kanban.KaerubanAPI.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID idUser,
        String name,
        String email,
        String picture,
        LocalDateTime createdAt
) {
    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPicture(),
                user.getCreatedAt()
        );
    }
}