package com.Tcc.Kanban.KaerubanAPI.dto.board;

import java.util.UUID;

public record BoardRequestDTO(
        String title,
        String color,
        UUID ownerId
) {}
