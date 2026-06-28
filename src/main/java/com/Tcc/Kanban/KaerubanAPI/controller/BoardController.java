package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.BoardRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<BoardResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(boardService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BoardResponseDTO>> getByUser(
            @PathVariable Integer userId,
            @RequestParam(required = false) String title) {
        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(boardService.findByUserAndTitle(userId, title));
        }
        return ResponseEntity.ok(boardService.findByUser(userId));
    }

    @GetMapping("/user/{userId}/member")
    public ResponseEntity<List<BoardResponseDTO>> getByMember(@PathVariable Integer userId) {
        return ResponseEntity.ok(boardService.findByMember(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> update(@PathVariable Integer id, @RequestBody BoardRequestDTO requestDTO) {
        return ResponseEntity.ok(boardService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{boardId}/members/{userId}")
    public ResponseEntity<BoardResponseDTO> addMember(@PathVariable Integer boardId, @PathVariable Integer userId) {
        return ResponseEntity.ok(boardService.addMember(boardId, userId));
    }

    @DeleteMapping("/{boardId}/members/{userId}")
    public ResponseEntity<BoardResponseDTO> removeMember(@PathVariable Integer boardId, @PathVariable Integer userId) {
        return ResponseEntity.ok(boardService.removeMember(boardId, userId));
    }
}