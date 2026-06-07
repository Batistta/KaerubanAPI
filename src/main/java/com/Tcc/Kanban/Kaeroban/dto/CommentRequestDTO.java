package com.Tcc.Kanban.Kaeroban.dto;

public record CommentRequestDTO(
        String content,
        Integer userId,
        Integer cardId
) {}