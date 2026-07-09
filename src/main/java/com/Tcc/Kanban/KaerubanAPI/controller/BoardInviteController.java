package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.invite.BoardInviteCreateDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.invite.BoardInviteRedeemDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.invite.BoardInviteResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.BoardInviteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invite")
@CrossOrigin
public class BoardInviteController {
    private final BoardInviteService boardInviteService;

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<BoardInviteResponseDTO>> getAllBoardInvites(@PathVariable UUID boardId) {
        return ResponseEntity.ok(boardInviteService.getAllInvitesByBoard(boardId));
    }
    @PostMapping
    public ResponseEntity<BoardInviteResponseDTO> generateInvite(@Valid @RequestBody BoardInviteCreateDTO requestDto) {
        return new ResponseEntity<>(boardInviteService.generate(requestDto),HttpStatus.CREATED);
    }

    @PostMapping("/redeem")
    public ResponseEntity<Void> redeem(@Valid @RequestBody BoardInviteRedeemDTO requestDto) {
        boardInviteService.redeem(requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boardInviteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}