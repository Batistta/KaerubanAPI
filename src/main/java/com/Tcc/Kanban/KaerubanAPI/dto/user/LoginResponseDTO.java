package com.Tcc.Kanban.KaerubanAPI.dto.user;

import java.util.UUID;

public record LoginResponseDTO(String token, UUID userId) {
}
