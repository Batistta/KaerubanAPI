package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.column.BoardColumnResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.column.BoardColumnSaveDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.column.BoardColumnUpdateDTO;
import com.Tcc.Kanban.KaerubanAPI.service.BoardColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/columns")
@CrossOrigin
public class BoardColumnController {
    private final BoardColumnService boardColumnService;

    @PostMapping
    public ResponseEntity<BoardColumnResponseDTO> save(@Valid @RequestBody BoardColumnSaveDTO requestDTO) {
        return new ResponseEntity<>(boardColumnService.save(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<BoardColumnResponseDTO>> getByBoard(@PathVariable UUID boardId) {
        return ResponseEntity.ok(boardColumnService.findByBoard(boardId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardColumnResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(boardColumnService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardColumnResponseDTO> update(@PathVariable UUID id, @RequestBody BoardColumnUpdateDTO requestDTO) {
        return ResponseEntity.ok(boardColumnService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boardColumnService.delete(id);
        return ResponseEntity.noContent().build();
    }
}