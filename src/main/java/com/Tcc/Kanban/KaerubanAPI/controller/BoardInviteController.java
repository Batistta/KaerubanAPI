package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.BoardInviteCreateDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardInviteRedeemDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardInviteResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.BoardInviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invite")
@CrossOrigin
public class BoardInviteController {
    private final BoardInviteService boardInviteService;

    @PostMapping
    public ResponseEntity<BoardInviteResponseDTO> generateInvite(BoardInviteCreateDTO requestDto) {
        return new ResponseEntity<>(boardInviteService.generate(requestDto),HttpStatus.CREATED);
    }

    @PostMapping("/redeem")
    public ResponseEntity<Void> redeem(BoardInviteRedeemDTO requestDto) {
        boardInviteService.redeem(requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boardInviteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
