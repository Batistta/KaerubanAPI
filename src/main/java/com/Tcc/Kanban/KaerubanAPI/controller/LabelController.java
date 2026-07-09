package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.label.LabelRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.label.LabelResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/labels")
@CrossOrigin
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    public ResponseEntity<LabelResponseDTO> save(@RequestBody LabelRequestDTO requestDTO) {
        return new ResponseEntity<>(labelService.save(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(labelService.findById(id));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<LabelResponseDTO> getByBoard(@PathVariable UUID boardId) {
        return ResponseEntity.ok(labelService.findByBoardId(boardId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<LabelResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(labelService.findByNameContaining(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelResponseDTO> update(@PathVariable UUID id, @RequestBody LabelRequestDTO requestDTO) {
        return ResponseEntity.ok(labelService.updateById(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        labelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
