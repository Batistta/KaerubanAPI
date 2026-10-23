package com.Tcc.Kanban.KaerubanAPI.dto.board;

import com.Tcc.Kanban.KaerubanAPI.dto.user.UserResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.Board;

import java.util.List;
import java.util.UUID;

public record BoardResponseDTO(
        UUID idBoard,
        String title,
        String color,
        UserResponseDTO owner,
        List<UserResponseDTO> members
) {
    public static BoardResponseDTO from(Board board) {
        UserResponseDTO owner = board.getMembers().stream()
                .filter(m -> "OWNER".equals(m.getRole().getTitle()))  // ajuste ao campo real de Role
                .findFirst()
                .map(m -> UserResponseDTO.from(m.getUser()))
                .orElse(null);

        return new BoardResponseDTO(
                board.getId(),
                board.getTitle(),
                board.getColor(),
                owner,
                board.getMembers().stream()
                        .map(m -> UserResponseDTO.from(m.getUser()))
                        .toList()
        );
    }
}