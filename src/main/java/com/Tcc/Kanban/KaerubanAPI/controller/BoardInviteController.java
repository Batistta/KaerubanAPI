package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.BoardInviteCreateDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardInviteRedeemDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardInviteResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.BoardInviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invite")
@CrossOrigin
public class BoardInviteController {
    private final BoardInviteService boardInviteService;

    @GetMapping("/{id}")
    public ResponseEntity<List<BoardInviteResponseDTO>> getAllBoardInvites(@PathVariable Integer id) {
        return new ResponseEntity<>(boardInviteService.getAllInvitesByBoard(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BoardInviteResponseDTO> generateInvite(@RequestBody BoardInviteCreateDTO requestDto) {
        return new ResponseEntity<>(boardInviteService.generate(requestDto),HttpStatus.CREATED);
    }

    @PostMapping("/redeem")
    public ResponseEntity<Void> redeem(@RequestBody BoardInviteRedeemDTO requestDto) {
        boardInviteService.redeem(requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boardInviteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}