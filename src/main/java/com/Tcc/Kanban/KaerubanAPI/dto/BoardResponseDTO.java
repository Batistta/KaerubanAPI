package com.Tcc.Kanban.KaerubanAPI.dto;

import java.util.List;

public record BoardResponseDTO(
        Integer idBoard,
        String title,
        String color,
        UserResponseDTO owner,
        List<UserResponseDTO> members
) {}