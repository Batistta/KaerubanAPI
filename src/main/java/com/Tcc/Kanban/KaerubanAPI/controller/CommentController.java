package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.comment.CommentRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.comment.CommentResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> save(@Valid @RequestBody CommentRequestDTO requestDTO) {
        return new ResponseEntity<>(commentService.save(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<CommentResponseDTO>> getByCard(@PathVariable UUID cardId) {
        return ResponseEntity.ok(commentService.findByCard(cardId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody CommentRequestDTO requestDTO) {
        return ResponseEntity.ok(commentService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
