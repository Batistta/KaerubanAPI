package com.Tcc.Kanban.KaerubanAPI.dto;

public record CommentRequestDTO(
        String content,
        Integer userId,
        Integer cardId
) {}