package com.Tcc.Kanban.Kaeroban.dto;

import java.util.List;

public record BoardResponseDTO(
        Integer idBoard,
        String title,
        String color,
        UserResponseDTO owner,
        List<UserResponseDTO> members
) {}