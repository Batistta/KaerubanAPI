package com.Tcc.Kanban.KaerubanAPI.dto.invite;

import com.Tcc.Kanban.KaerubanAPI.model.BoardInvite;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record BoardInviteResponseDTO(
        UUID id,
        UUID boardId,
        String boardTitle,
        String code,
        LocalDate expiresAt,
        LocalDateTime createdAt
) {
    public static BoardInviteResponseDTO from(BoardInvite invite) {
        return new BoardInviteResponseDTO(
                invite.getId(),
                invite.getBoard().getId(),
                invite.getBoard().getTitle(),
                invite.getCode(),
                invite.getExpiresAt(),
                invite.getCreatedAt()
        );
    }
}