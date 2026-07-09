package com.Tcc.Kanban.KaerubanAPI.dto.board;

import com.Tcc.Kanban.KaerubanAPI.dto.user.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public record BoardResponseDTO(
        UUID idBoard,
        String title,
        String color,
        UserResponseDTO owner,
        List<UserResponseDTO> members
) {}