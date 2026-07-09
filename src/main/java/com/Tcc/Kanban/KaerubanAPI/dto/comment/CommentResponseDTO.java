package com.Tcc.Kanban.KaerubanAPI.dto.comment;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponseDTO(
        UUID idComment,
        String content,
        LocalDateTime createdAt,
        UUID userId,
        String userName,
        UUID cardId
) {}