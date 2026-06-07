package com.Tcc.Kanban.Kaeroban.dto;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        Integer idComents,
        String content,
        LocalDateTime createdAt,
        Integer userId,
        String userName,
        Integer cardId
) {}