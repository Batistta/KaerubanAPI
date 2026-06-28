package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.BoardColumnRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardColumnResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.BoardColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/columns")
@CrossOrigin
public class BoardColumnController {
    private final BoardColumnService boardColumnService;

    @PostMapping
    public ResponseEntity<BoardColumnResponseDTO> save(@RequestBody BoardColumnRequestDTO requestDTO) {
        return new ResponseEntity<>(boardColumnService.save(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<BoardColumnResponseDTO>> getByBoard(@PathVariable Integer boardId) {
        return ResponseEntity.ok(boardColumnService.findByBoard(boardId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardColumnResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(boardColumnService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardColumnResponseDTO> update(@PathVariable Integer id, @RequestBody BoardColumnRequestDTO requestDTO) {
        return ResponseEntity.ok(boardColumnService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boardColumnService.delete(id);
        return ResponseEntity.noContent().build();
    }
}