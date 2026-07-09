package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.board.BoardRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.board.BoardResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@CrossOrigin
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseDTO> save(@RequestBody BoardRequestDTO requestDTO) {
        return new ResponseEntity<>(boardService.save(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(boardService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BoardResponseDTO>> getByUser(
            @PathVariable UUID userId,
            @RequestParam(required = false) String title) {
        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(boardService.findByUserAndTitle(userId, title));
        }
        return ResponseEntity.ok(boardService.findByUser(userId));
    }

    @GetMapping("/user/{userId}/member")
    public ResponseEntity<List<BoardResponseDTO>> getByMember(@PathVariable UUID userId) {
        return ResponseEntity.ok(boardService.findByMember(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> update(@PathVariable UUID id, @RequestBody BoardRequestDTO requestDTO) {
        return ResponseEntity.ok(boardService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardId}/members/{userId}")
    public ResponseEntity<BoardResponseDTO> removeMember(@PathVariable UUID boardId, @PathVariable UUID userId) {
        return ResponseEntity.ok(boardService.removeMember(boardId, userId));
    }
}