package com.Tcc.Kanban.KaerubanAPI.dto;

import com.Tcc.Kanban.KaerubanAPI.model.BoardInvite;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BoardInviteResponseDTO(
        Integer id,
        Integer boardId,
        String boardTitle,
        String code,
        LocalDate expiresAt,
        LocalDateTime createdAt
) {
    public static BoardInviteResponseDTO from(BoardInvite invite) {
        return new BoardInviteResponseDTO(
                invite.getIdInvite(),
                invite.getBoard().getIdBoard(),
                invite.getBoard().getTitle(),
                invite.getCode(),
                invite.getExpiresAt(),
                invite.getCreatedAt()
        );
    }
}